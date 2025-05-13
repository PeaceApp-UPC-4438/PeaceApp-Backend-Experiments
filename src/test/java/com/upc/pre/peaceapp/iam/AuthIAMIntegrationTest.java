package com.upc.pre.peaceapp.iam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.pre.peaceapp.security.iam.domain.model.aggregates.User;
import com.upc.pre.peaceapp.security.iam.infrastructure.persistence.jpa.repositories.UserIAMRepository;
import com.upc.pre.peaceapp.security.iam.interfaces.rest.resources.SignUpResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@WebMvcTest
public class AuthIAMIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserIAMRepository userRepository;

    @Test
    void whenSignUpWithValidData_thenUserIsPersistedInDatabase() throws Exception {
        // 🔹 ARRANGE
        SignUpResource signUp = new SignUpResource("testuser", "secure123", Collections.singletonList("ROLE_USER"));

        // 🔹 ACT
        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUp)))
                .andExpect(status().isCreated());

        // 🔹 ASSERT
        Optional<User> savedUser = userRepository.findByUsername("testuser");
        assertTrue(savedUser.isPresent(), "El usuario debería haberse guardado en la base");
        assertEquals("testuser", savedUser.get().getUsername());
    }
}
