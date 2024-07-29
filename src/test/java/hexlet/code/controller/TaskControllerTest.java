package hexlet.code.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.TaskCreatedDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    private User testUser;

    private TaskStatus testTaskStatus;

    private Task testTask;

    private Label testLabel;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void setup() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));

        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        testTaskStatus = Instancio.of(modelGenerator.getStatusModel()).create();
        testTask = Instancio.of(modelGenerator.getTaskModel()).create();
        testLabel = Instancio.of(modelGenerator.getLabelModel()).create();


        userRepository.save(testUser);
        taskStatusRepository.save(testTaskStatus);
        labelRepository.save(testLabel);

        testTask.setAssignee(testUser);
        testTask.setTaskStatus(testTaskStatus);
        testTask.setLabels(new ArrayList<>(List.of(testLabel)));

        taskRepository.save(testTask);
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/api/tasks").with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        mockMvc.perform(get("/api/tasks/" + testTask.getId()).with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        var taskStatus = taskStatusRepository.findBySlug("to_review").get();
        var label = labelRepository.findByName("feature").get();
        var data = new TaskCreatedDTO();
        String name = "New Task Name";
        data.setTitle(name);
        data.setSlug(taskStatus.getSlug());

        var request = post("/api/tasks")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isCreated());
        var task = taskRepository.findByName(data.getTitle()).get();

        assertNotNull(task);
        assertThat(task.getName()).isEqualTo(data.getTitle());
        assertThat(task.getTaskStatus().getSlug()).isEqualTo(data.getSlug());
    }

    @Test
    public void testUpdate() throws Exception {
        var updatedData = new HashMap<String, String>();
        updatedData.put("title", "news");
        updatedData.put("content", "contents");

        var request = put("/api/tasks/" + testTask.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updatedData));

        mockMvc.perform(request).andExpect(status().isOk());

        var updatedTask = taskRepository.findById(testTask.getId()).get();

        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getName()).isEqualTo(updatedData.get("title"));
        assertThat(updatedTask.getIndex()).isEqualTo(testTask.getIndex());
    }

    @Test
    public void testCreateWithInvalidData() throws Exception {
        var fakeTask = new HashMap<String, String>();
        fakeTask.put("title", "");
        fakeTask.put("slug", "");
        var request = post("/api/tasks")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(fakeTask));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }



    @Test
    public void testDestroy() throws Exception {
        mockMvc.perform(delete("/api/tasks/" + testTask.getId()).with(token))
                .andExpect(status().isNoContent());
    }
}
