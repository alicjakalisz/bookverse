package com.bookverse.bookverse.controller;

import com.bookverse.bookverse.dto.UserDto;
import com.bookverse.bookverse.model.User;
import com.bookverse.bookverse.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //You need to make sure this test class is in the same package or a subpackage of BookverseApplication, OR tell Spring explicitly where to look:
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        UserDto newUser = UserDto.builder()
                .username("Jan Kowalski")
                .build();

        String jsonBody = objectMapper.writeValueAsString(newUser);
        String responseBody = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))// POST needs this
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("Jan Kowalski"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto createdUser = objectMapper.readValue(responseBody, UserDto.class);// Convert DTO to JSON

    }




    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        // given
        User user = User.builder()
                .name("Anna Nowak")
                .build();
        user = userRepository.save(user);

        // when & then
        mockMvc.perform(get("/api/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getName())); //controller returns dto , in dto name is username
    }

    @Test
    void deleteUser_ShouldRemoveUser() throws Exception {
        User user = User.builder()
                .name("Piotr Zalewski")
                .build();
        user = userRepository.save(user);

        //checking if saved by get endpoint:
        mockMvc.perform(get("/api/users/{id}", user.getId()))
                        .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value("Piotr Zalewski"));


        //deleting
        mockMvc.perform(delete("/api/users/{id}", user.getId()))
                .andExpect(status().isNoContent());

        Assertions.assertFalse(userRepository.existsById(user.getId()));
    }

    // Możesz dopisać testy update i getAll
}

