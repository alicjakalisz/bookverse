package com.bookverse.bookverse.controller;

import com.bookverse.bookverse.dto.AuthorDto;
import com.bookverse.bookverse.model.Author;
import com.bookverse.bookverse.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-test.yml") //if you dont use H2 with application-test config  not necessary
@Transactional
class AuthorControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AuthorRepository authorRepository;

    @Test
    void createAuthor_ShouldReturnCreatedAuthor() throws Exception {
        AuthorDto dto = AuthorDto.builder().name("George Orwell").build();
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("George Orwell"));
    }

    @Test
    void getAllAuthors_ShouldReturnList() throws Exception {
        authorRepository.save(Author.builder().name("A").build());
        authorRepository.save(Author.builder().name("B").build());

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void updateAuthor_ShouldReturnUpdatedAuthor() throws Exception {
        Author author = authorRepository.save(Author.builder().name("Old").build());

        AuthorDto dto = AuthorDto.builder().name("Updated").build();
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/api/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void deleteAuthor_ShouldRemoveAuthor() throws Exception {
        Author author = authorRepository.save(Author.builder().name("Delete Me").build());

        mockMvc.perform(delete("/api/authors/" + author.getId()))
                .andExpect(status().isNoContent());
    }
}

