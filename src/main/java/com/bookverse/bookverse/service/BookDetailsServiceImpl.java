package com.bookverse.bookverse.service;

import com.bookverse.bookverse.dto.BookDetailsDto;
import com.bookverse.bookverse.dto.OpenLibraryResponseDto;
import com.bookverse.bookverse.mapper.BookDetailsMapper;
import com.bookverse.bookverse.model.Book;
import com.bookverse.bookverse.model.BookDetails;
import com.bookverse.bookverse.repository.BookDetailsRepository;
import com.bookverse.bookverse.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookDetailsServiceImpl implements BookDetailsService {


    private BookDetailsRepository repository;
    private  BookDetailsMapper mapper;
    private BookRepository bookRepository;
    private RestTemplate restTemplate;

    @Autowired
    public BookDetailsServiceImpl(BookDetailsRepository repository, BookDetailsMapper mapper, BookRepository bookRepository, RestTemplate restTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.bookRepository = bookRepository;
        this.restTemplate = restTemplate;
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

    //Example use case - you need controller:
    //
    //A book has already been added to your database (via a POST /books request), but the user now wants to attach official metadata (description, publish date, cover) by providing an ISBN.

    @Override
    public BookDetailsDto fetchAndSaveByIsbn(String isbn, Long bookId) {
        String url = "https://openlibrary.org/isbn/" + isbn + ".json";
        ResponseEntity<OpenLibraryResponseDto> response = restTemplate.getForEntity(url, OpenLibraryResponseDto.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Book details not found for ISBN: " + isbn);
        }
        OpenLibraryResponseDto data = response.getBody();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        BookDetails details = BookDetails.builder()
                .description(data.getDescription())
                .publishDate(data.getPublishDate())
                //Open Library maintains a public cover image service, and they explain this format in their documentation:
                //
                //Base URL: https://covers.openlibrary.org
                //
                //Path: /b/id/{coverId}-{size}.jpg
                //
                //Where:
                //
                //{coverId} is the numeric ID you get from covers in the JSON (e.g. 11403183)
                //
                //{size} can be:
                //
                //S – Small
                //
                //M – Medium
                //
                //L – Large
                //
                //🔗 Example from their docs or usage:
                //
                //ruby
                //Copy
                //Edit
                //https://covers.openlibrary.org/b/id/11403183-L.jpg
                .imageUrl(data.getCovers() != null && !data.getCovers().isEmpty()
                        ? "https://covers.openlibrary.org/b/id/" + data.getCovers().get(0) + "-S.jpg"
                        : null)
                .book(book)
                .build();
        book.setBookDetails(details); // 💡 This links the two properly
        bookRepository.save(book); // 💾 Save the owning side!
        return mapper.toDto(details);

    }

    @Override
    public List<BookDetailsDto> findAll() {
        return repository.findAll().stream().map(entity -> mapper.toDto(entity)).toList();
    }






}
