package com.bookverse.bookverse.service;

import com.bookverse.bookverse.dto.AuthorDto;


import java.util.List;

public interface AuthorService {

    AuthorDto getAuthorById(Long id);
    List<AuthorDto> getAllAuthors();
    AuthorDto saveAuthor(AuthorDto authorDto);
    void deleteAuthor(Long id);
}
