package hexlet.code.app.component;

import hexlet.code.app.dto.LabelCreatedDTO;
import hexlet.code.app.dto.TaskStatusCreatedDTO;
import hexlet.code.app.model.Label;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.service.CustomUserDetailsService;
import hexlet.code.app.service.LabelService;
import hexlet.code.app.service.TaskStatusService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private final CustomUserDetailsService userService;

    @Autowired
    private final TaskStatusService taskStatusService;

    @Autowired
    private final LabelService labelService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    private final LabelRepository labelRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findByEmail("hexlet@example.com").isEmpty()) {
            var email = "hexlet@example.com";
            var userData = new User();
            userData.setEmail(email);
            userData.setPassword("qwerty");
            userService.createUser(userData);
        }

        var defaultStatuses = new ArrayList<TaskStatusCreatedDTO>();
        defaultStatuses.add(new TaskStatusCreatedDTO("Draft", "draft"));
        defaultStatuses.add(new TaskStatusCreatedDTO("toReview", "to_review"));
        defaultStatuses.add(new TaskStatusCreatedDTO("toBeFixed", "to_be_fixed"));
        defaultStatuses.add(new TaskStatusCreatedDTO("toPublish", "to_publish"));
        defaultStatuses.add(new TaskStatusCreatedDTO("Published", "published"));
        var currentStatuses = taskStatusRepository.findAll().stream().map(TaskStatus::getSlug).toList();
        for (var status : defaultStatuses) {
            if (!currentStatuses.contains(status.getSlug())) {
                taskStatusService.create(status);
            }
        }

        var defaultLabels = new ArrayList<LabelCreatedDTO>();
        defaultLabels.add(new LabelCreatedDTO("feature"));
        defaultLabels.add(new LabelCreatedDTO("bug"));
        var currentLabels = labelRepository.findAll().stream().map(Label::getName).toList();
        for (var label : defaultLabels) {
            if (!currentLabels.contains(label.getName())) {
                labelService.create(label);
            }
        }
    }
}
