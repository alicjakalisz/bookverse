package com.bookverse.bookverse.controller;


import com.bookverse.bookverse.dto.ReviewDto;
import com.bookverse.bookverse.model.Author;
import com.bookverse.bookverse.model.Book;
import com.bookverse.bookverse.model.Review;
import com.bookverse.bookverse.model.User;
import com.bookverse.bookverse.repository.AuthorRepository;
import com.bookverse.bookverse.repository.BookRepository;
import com.bookverse.bookverse.repository.ReviewRepository;
import com.bookverse.bookverse.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-test.yml")//if you dont use H2 with application-test config  not necessary
@Transactional
class ReviewControllerIntegrationTest extends AbstractIntegrationTest{

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private AuthorRepository authorRepository;

    @Test
    void createReview_ShouldReturnCreatedReview() throws Exception {
        Author author = authorRepository.save(Author.builder().name("X").build());
        Book book = bookRepository.save(Book.builder().title("T").isbn("123").author(author).build());
        User user = userRepository.save(User.builder().name("Y").build());

        ReviewDto dto = ReviewDto.builder()
                .stars(5)
                .content("Amazing")
                .bookId(book.getId())
                .userId(user.getId())
                .build();

        String json = objectMapper.writeValueAsString(dto);

        String contentAsString = mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ReviewDto reviewDto = objectMapper.readValue(contentAsString, ReviewDto.class);
        Assertions.assertEquals(5,reviewDto.getStars());
        Assertions.assertEquals("Amazing",reviewDto.getContent());

    }

    @Test
    void getReviewsByBookId_ShouldReturnList() throws Exception {
        Author author = authorRepository.save(Author.builder().name("X").build());
        Book book = bookRepository.save(Book.builder().title("T").isbn("123").author(author).build());
        User user = userRepository.save(User.builder().name("Y").build());

        reviewRepository.save(Review.builder().content("Great!").rating(4).book(book).user(user).build());

        String contentAsString = mockMvc.perform(get("/api/reviews/" + book.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ReviewDto reviewDto = objectMapper.readValue(contentAsString, ReviewDto.class);

        Assertions.assertEquals("Great!", reviewDto.getContent());

    }
}
