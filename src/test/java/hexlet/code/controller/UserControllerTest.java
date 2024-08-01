package hexlet.code.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import hexlet.code.dto.UserCreatedDTO;
import hexlet.code.dto.UserUpdatedDTO;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    private User testUser;

    private JwtRequestPostProcessor token;

    @BeforeEach
    public void setup() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));

        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/api/users").with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(get("/api/users/" + testUser.getId()).with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        var createdUser = new UserCreatedDTO();
        createdUser.setPassword("zxc123");
        createdUser.setEmail("denis@gmail.com");

        var request = post("/api/users")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(createdUser));

        mockMvc.perform(request).andExpect(status().isCreated());

        var user = userRepository.findByEmail(createdUser.getEmail()).get();

        assertNotNull(user);
        assertThat(user.getEmail()).isEqualTo(createdUser.getEmail());
    }

    @Test
    public void testUpdate() throws Exception {
        var updatedData = new UserUpdatedDTO();
        var currentToken = jwt().jwt(builder -> builder.subject(testUser.getEmail()));
        updatedData.setEmail(JsonNullable.of("2008deous@gmail.com"));

        var request = put("/api/users/" + testUser.getId())
                .with(currentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updatedData));

        mockMvc.perform(request).andExpect(status().isOk());

        var updatedUser = userRepository.findById(testUser.getId()).get();

        assertThat(updatedUser.getEmail()).isEqualTo(updatedData.getEmail().get());
    }

    @Test
    public void testCreateWithInvalidData() throws Exception {
        var fakeUser = new HashMap<String, String>();
        fakeUser.put("email", "2");
        fakeUser.put("password", "0");
        var request = post("/api/users")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(fakeUser));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void testDelete() throws Exception {
        var currentToken = jwt().jwt(builder -> builder.subject(testUser.getEmail()));
        mockMvc.perform(delete("/api/users/" + testUser.getId()).with(currentToken))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteWithInvalidUser() throws Exception {
        mockMvc.perform(delete("/api/users/" + testUser.getId()).with(token))
                .andExpect(status().isForbidden());
    }
}
