package com.bookverse.bookverse.service;

import com.bookverse.bookverse.dto.AuthorDto;
import com.bookverse.bookverse.mapper.AuthorMapper;
import com.bookverse.bookverse.model.Author;
import com.bookverse.bookverse.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorServiceImpl implements AuthorService{

    private AuthorRepository authorRepository;
    private AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
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

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        Long authorId = authorDto.getId();
        Author existingAuthor = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + authorId + " not found"));

        // Update the fields you want to allow to be changed
        existingAuthor.setName(authorDto.getName());
        // If there are other fields, update them here

        Author saved = authorRepository.save(existingAuthor);
        return authorMapper.toDto(saved);
    }
}
