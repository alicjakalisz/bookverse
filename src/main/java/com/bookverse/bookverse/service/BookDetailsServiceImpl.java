package com.bookverse.bookverse.service;

import com.bookverse.bookverse.dto.BookDetailsDto;
import com.bookverse.bookverse.mapper.BookDetailsMapper;
import com.bookverse.bookverse.model.Book;
import com.bookverse.bookverse.model.BookDetails;
import com.bookverse.bookverse.repository.BookDetailsRepository;
import com.bookverse.bookverse.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDetailsServiceImpl implements BookDetailsService {


    private BookDetailsRepository repository;
    private  BookDetailsMapper mapper;
    private BookRepository bookRepository;

    @Autowired
    public BookDetailsServiceImpl(BookDetailsRepository repository, BookDetailsMapper mapper, BookRepository bookRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.bookRepository = bookRepository;
    }


    @Override
    public BookDetailsDto create(BookDetailsDto dto) {
        BookDetails entity = mapper.toEntity(dto);

        Book book = bookRepository.findById(dto.getBookId()).orElseThrow(() -> new EntityNotFoundException("Book with this id not found"));

        entity.setBook(book); // optional — for full bidirectional sync
        // Set the relation from the owning side (book owns details)
        book.setBookDetails(entity);

        // Save the book (this will cascade BookDetails)
        bookRepository.save(book);
        return mapper.toDto(entity);
    }

    @Override
    public BookDetailsDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("BookDetails not found"));
    }



    @Override
    public BookDetailsDto update(Long id, BookDetailsDto dto) {
        BookDetails existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BookDetails not found"));
        existing.setDescription(dto.getDescription());
        existing.setImageUrl(dto.getImageUrl());
       //set book

        return mapper.toDto(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<BookDetailsDto> findAll() {
        return repository.findAll().stream().map(entity -> mapper.toDto(entity)).toList();
    }






}
