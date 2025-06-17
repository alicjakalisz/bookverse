package com.bookverse.bookverse.service;

import com.bookverse.bookverse.dto.AuthorDto;
import com.bookverse.bookverse.mapper.AuthorMapper;
import com.bookverse.bookverse.model.Author;
import com.bookverse.bookverse.repository.AuthorRepository;
import com.bookverse.bookverse.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorServiceImpl implements AuthorService{

    private AuthorRepository authorRepository;
    private AuthorMapper authorMapper;
    private BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public AuthorDto getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(entity -> authorMapper.toDto(entity))
                .orElseThrow(()-> new EntityNotFoundException("Author with id: " + id + " not found"));
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository
                .findAll()
                .stream()
                .map(author -> authorMapper.toDto(author)).toList();
    }

    @Override
    public AuthorDto saveAuthor(AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        // find associated books in db for this author???
        //AuthoDto doesnt have relation with books displayed...when you create a book. you assign author
        Author saved = authorRepository.save(author);
        return authorMapper.toDto(saved);
    }

    @Override
    public void deleteAuthor(Long id) {
    if(!authorRepository.existsById(id)){
        throw new EntityNotFoundException("Author with id: " +  id +  "not found");
    }
    authorRepository.deleteById(id);
    }
}
