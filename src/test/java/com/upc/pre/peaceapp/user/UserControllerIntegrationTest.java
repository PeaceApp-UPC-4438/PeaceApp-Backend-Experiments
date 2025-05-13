package com.upc.pre.peaceapp.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.pre.peaceapp.config.TestAuthHelper;
import com.upc.pre.peaceapp.models.UserProfile;
import com.upc.pre.peaceapp.schemas.UpdateUserProfileSchema;
import com.upc.pre.peaceapp.schemas.UserProfileSchema;
import com.upc.pre.peaceapp.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private TestAuthHelper authHelper;

    @Test
    void whenCreateUser_thenReturnsUser() throws Exception {
        // 🔹 Arrange
        UserProfileSchema newUser = new UserProfileSchema(
                "John", "Doe", "1234", "john@example.com", "pwrd123", "1", null
        );
        String token = authHelper.registerAndAuthenticateTestUser("john@example.com", "password123");

        // 🔹 Act & Assert
        mockMvc.perform(post("/api/v1/users")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void whenGetUserByEmail_thenReturnsUser() throws Exception {
        // 🔹 Arrange
        UserProfile user = new UserProfile("Jane", "jane@example.com", "password", "Smith", "999888777", "2", null);
        userService.save(user);

        String token = authHelper.registerAndAuthenticateTestUser("jane@example.com", "password");


        // 🔹 Act & Assert
        mockMvc.perform(get("/api/v1/users/jane@example.com")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jane@example.com"));
    }

    @Test
    void whenUpdateUser_thenReturnsUpdatedUser() throws Exception {
        // 🔹 Arrange
        UserProfile saved = userService.save(new UserProfile("Tom", "tom@example.com", "pass", "White", "555444333", "3", null));

        UpdateUserProfileSchema update = new UpdateUserProfileSchema(
                "Tommy", "newlastname", "123456789", null
        );

        String token = authHelper.registerAndAuthenticateTestUser("tom@example.com", "password123");

        // 🔹 Act & Assert
        mockMvc.perform(put("/api/v1/users/" + saved.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tommy"));
    }

    @Test
    void whenDeleteUser_thenUserIsRemoved() throws Exception {
        // 🔹 Arrange
        UserProfile saved = userService.save(new UserProfile("Delete", "del@example.com", "pass", "User", "000111222", "4", null));

        String token = authHelper.registerAndAuthenticateTestUser("del@example.com", "password123");

        // 🔹 Act
        mockMvc.perform(delete("/api/v1/users/" + saved.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted in both tables"));

        // 🔹 Assert
        Optional<UserProfile> deleted = userService.findOptionalById(saved.getId());
        assertThat(deleted).isEmpty();
    }
}
