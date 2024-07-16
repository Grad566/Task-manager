package hexlet.code.app.component;

import hexlet.code.app.dto.UserCreatedDTO;
import hexlet.code.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var data = new UserCreatedDTO();
        data.setEmail("hexlet@example.com");
        data.setPassword("qwerty");
        userService.create(data);
    }
}
