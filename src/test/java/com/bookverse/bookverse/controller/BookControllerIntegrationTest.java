package com.bookverse.bookverse.controller;

import com.bookverse.bookverse.dto.BookDto;
import com.bookverse.bookverse.model.Author;
import com.bookverse.bookverse.model.Book;
import com.bookverse.bookverse.repository.AuthorRepository;
import com.bookverse.bookverse.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
class BookControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AuthorRepository authorRepository;
    @Autowired private BookRepository bookRepository;

    @Test
    void createBook_ShouldReturnCreatedBook() throws Exception {
        Author author = authorRepository.save(Author.builder().name("Author A").build());

        BookDto dto = BookDto.builder()
                .title("Fanstatic Mr Fox")
                .isbn("9780140328721")
                .authorId(author.getId())
                .build();

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Fanstatic Mr Fox"))
                .andExpect(jsonPath("$.authorId").value(author.getId()));
    }

    @Test
    void getBooksByTitle_ShouldReturnMatchingBooks() throws Exception {
        Author author = authorRepository.save(Author.builder().name("Test").build());
        bookRepository.save(Book.builder().title("Spring in Action").isbn("111").author(author).reviews(new ArrayList<>()).build());
        bookRepository.save(Book.builder().title("Spring Boot").isbn("222").author(author).reviews(new ArrayList<>()).build());

        mockMvc.perform(get("/api/books/search?title=Spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}

