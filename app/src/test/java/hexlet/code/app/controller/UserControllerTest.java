package hexlet.code.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.service.UserService;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.datafaker.Faker;

import java.util.HashMap;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    private User testUser;

    private JwtRequestPostProcessor token;

    @BeforeEach
    public void setup() {
        testUser = Instancio.of(User.class)
                .ignore(Select.field(User::getUpdatedAt))
                .ignore(Select.field(User::getCreatedAt))
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getFirstName), () -> faker.name().firstName())
                .supply(Select.field(User::getLastName), () -> faker.name().lastName())
                .supply(Select.field(User::getPassword), () -> faker.internet().password())
                .create();
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/api/users").with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {

        var request = post("/api/users")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(testUser));

        mockMvc.perform(request).andExpect(status().isCreated());

        var user = userRepository.findByEmail(testUser.getEmail()).get();

        assertNotNull(user);
        assertThat(user.getFirstName()).isEqualTo(testUser.getFirstName());
        assertThat(user.getLastName()).isEqualTo(testUser.getLastName());
        assertThat(user.getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    public void testUpdate() throws Exception {
        var updatedData = new HashMap<String, String>();
        updatedData.put("email", "2008deous@gmail.com");

        userRepository.save(testUser);

        var request = put("/api/users/" + testUser.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updatedData));

        mockMvc.perform(request).andExpect(status().isOk());

        testUser = userRepository.findById(testUser.getId()).get();

        assertThat(testUser.getEmail()).isEqualTo(updatedData.get("email"));
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
    public void testShow() throws Exception {
        userRepository.save(testUser);

        mockMvc.perform(get("/api/users/" + testUser.getId()).with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testDestroy() throws Exception {
        userRepository.save(testUser);

        mockMvc.perform(delete("/api/users/" + testUser.getId()).with(token))
                .andExpect(status().isNoContent());
    }
}
