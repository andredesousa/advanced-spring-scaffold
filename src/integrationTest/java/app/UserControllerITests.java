package app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import app.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Integration Tests")
public class UserControllerITests {

    @Autowired
    transient MockMvc mockMvc;

    @MockBean
    transient UserRepository userRepository;

    transient String bearerToken =
        "Bearer eyJhbGciOiJIUzUxMiJ9." +
        "eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTYzMzc5NDQwMSwiZXhwIjoxOTQ5MTU0NDAxLCJhdXRob3JpdGllcyI6W119." +
        "KR1DBB-ui8ycBhIcRhzOwhcqCNC2DTy5aDYlKeARg1_I0-Aa_KiBHvfZEJbsH4oO3vQxn5yaHmnxtIrlJOtoiQ";

    @Test
    @DisplayName("/user (GET)")
    void getAllUsers() throws Exception {
        MvcResult response = mockMvc.perform(get("/user").header(AUTHORIZATION, bearerToken)).andReturn();

        assertThat(response.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    @DisplayName("/user/{id} (GET)")
    void getUserById() throws Exception {
        MvcResult response = mockMvc.perform(get("/user/1").header(AUTHORIZATION, bearerToken)).andReturn();

        assertThat(response.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    @DisplayName("/user (POST)")
    void addUser() throws Exception {
        String user = "{\"username\":\"username\",\"password\":\"12345678\",\"email\":\"string@string.com\"}";
        MvcResult response = mockMvc
            .perform(post("/user").header(AUTHORIZATION, bearerToken).content(user).contentType(APPLICATION_JSON))
            .andReturn();

        assertThat(response.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    @DisplayName("/user/{id} (PUT)")
    void updateUser() throws Exception {
        String user = "{\"username\":\"username\",\"password\":\"12345678\",\"email\":\"string@string.com\"}";
        MvcResult response = mockMvc
            .perform(put("/user/1").header(AUTHORIZATION, bearerToken).content(user).contentType(APPLICATION_JSON))
            .andReturn();

        assertThat(response.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    @DisplayName("/user/{id} (DELETE)")
    void deleteUser() throws Exception {
        MvcResult response = mockMvc.perform(delete("/user/1").header(AUTHORIZATION, bearerToken)).andReturn();

        assertThat(response.getResponse().getStatus()).isEqualTo(200);
    }
}
