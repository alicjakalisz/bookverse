package com.bookverse.bookverse.service;

import com.bookverse.bookverse.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(Long id);
    List<BookDto> findAll();
    BookDto create(BookDto bookDto);
    BookDto update(Long id, BookDto bookDto);
    void delete(Long id);
}
