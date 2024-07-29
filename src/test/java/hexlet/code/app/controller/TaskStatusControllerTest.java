package hexlet.code.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

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
class TaskStatusControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskStatusRepository repository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    private TaskStatus testTaskStatus;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void setup() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));

        testTaskStatus = Instancio.of(modelGenerator.getStatusModel()).create();

        repository.save(testTaskStatus);
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/api/task_statuses").with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        mockMvc.perform(get("/api/task_statuses/" + testTaskStatus.getId()).with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        Map<String, String> data = new HashMap<>(Map.of(
                "name", "testStats",
                "slug", "testStats"
        ));

        var request = post("/api/task_statuses")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isCreated());

        var taskStatus = repository.findBySlug(data.get("slug")).get();

        assertNotNull(taskStatus);
        assertThat(taskStatus.getName()).isEqualTo(data.get("name"));
        assertThat(taskStatus.getSlug()).isEqualTo(data.get("slug"));
    }

    @Test
    public void testUpdate() throws Exception {
        var updatedData = new HashMap<String, String>();
        updatedData.put("name", "newses");

        var request = put("/api/task_statuses/" + testTaskStatus.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updatedData));

        mockMvc.perform(request).andExpect(status().isOk());

        var updatedTask = repository.findById(testTaskStatus.getId()).get();

        assertNotNull(updatedTask);
        assertThat(updatedTask.getName()).isEqualTo(updatedData.get("name"));
    }

    @Test
    public void testCreateWithInvalidData() throws Exception {
        var fakeTask = new HashMap<String, String>();
        fakeTask.put("name", "");
        fakeTask.put("slug", "");
        var request = post("/api/task_statuses")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(fakeTask));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void testDestroy() throws Exception {
        mockMvc.perform(delete("/api/task_statuses/" + testTaskStatus.getId()).with(token))
                .andExpect(status().isNoContent());
    }

}
