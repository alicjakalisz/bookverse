package com.bookverse.bookverse.service;

import com.bookverse.bookverse.dto.BookDetailsDto;

import java.util.List;

public interface BookDetailsService {
    BookDetailsDto findById(Long id);
    List<BookDetailsDto> findAll();
    BookDetailsDto create(BookDetailsDto dto);
    BookDetailsDto update(Long id, BookDetailsDto dto);
    void delete(Long id);
}
