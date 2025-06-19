package com.bookverse.bookverse.service;

import com.bookverse.bookverse.dto.BookDetailsDto;
import com.bookverse.bookverse.mapper.BookDetailsMapper;
import com.bookverse.bookverse.repository.BookDetailsRepository;
import com.bookverse.bookverse.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

//TODO
class BookDetailsServiceImplTest {

    @Mock
    private BookDetailsRepository repository;
    @Mock
    private BookDetailsMapper mapper;
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookDetailsServiceImpl bookDetailsService;

    @Test
    void create() {
        BookDetailsDto dto = new BookDetailsDto();
        dto.setBookId(1l);
        dto.setDescription("Book Details");


        //under the test

    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }
}