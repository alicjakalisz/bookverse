package com.bookverse.bookverse.service;

import com.bookverse.bookverse.dto.BookDto;
import com.bookverse.bookverse.mapper.BookMapper;
import com.bookverse.bookverse.model.Author;
import com.bookverse.bookverse.model.Book;
import com.bookverse.bookverse.model.Review;
import com.bookverse.bookverse.repository.AuthorRepository;
import com.bookverse.bookverse.repository.BookRepository;
import com.bookverse.bookverse.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;
    private BookMapper bookMapper;
    private AuthorRepository authorRepository;
    private ReviewRepository reviewRepository;
    private BookDetailsService bookDetailsService;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper, AuthorRepository authorRepository, ReviewRepository reviewRepository, BookDetailsService bookDetailsService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.authorRepository = authorRepository;
        this.reviewRepository = reviewRepository;
        this.bookDetailsService = bookDetailsService;
    }

    @Override
    public BookDto findById(Long id) {
        return bookRepository.findById(id).map(entity -> bookMapper.toDto(entity))
                .orElseThrow(()-> new EntityNotFoundException("Book with id: " + id + " not found"));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(book -> bookMapper.toDto(book)).toList();
    }

    @Override
    public BookDto create(BookDto bookDto) {
        Book entity = bookMapper.toEntity(bookDto); // all basic mapping (no relations)
        //obtain associated relations like reviews, author for this book from db:
        Author bookAuthor = authorRepository.findById(bookDto.getAuthorId()).orElseThrow();
        entity.setAuthor(bookAuthor);

        // Resolve Reviews from list of IDs (optional)
        if (bookDto.getReviewsId() != null && !bookDto.getReviewsId().isEmpty()) {
            List<Review> reviews = reviewRepository.findAllById(bookDto.getReviewsId());
            // Set the book reference in each review (important bi-directional relation fix)
            for (Review review : reviews) { // Review owns the book (Review has foreign key of book, Book has mappedBy)
                review.setBook(entity);
            }
            entity.setReviews(reviews);
        }

        // 💾 Save the book first to get a generated ID (if not in the fetch method will have null for book it tries to find
        Book saved = bookRepository.save(entity);

        // 📚 Now fetch details using the generated ID (inside will resave the book) -> DTO with request has ISBN and later on id is generated
        bookDetailsService.fetchAndSaveByIsbn(saved.getIsbn(), saved.getId());

        return bookMapper.toDto(saved);
    }

    @Override
    public BookDto update(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id: " + id + " not found"));
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());
        book.setPublicationYear(bookDto.getPublicationYear());
        //get review list and author from db based on dtos ids:

        Author bookAuthor = authorRepository.findById(bookDto.getAuthorId()).orElseThrow();
        book.setAuthor(bookAuthor);

        // Resolve Reviews from list of IDs (optional)
        if (bookDto.getReviewsId() != null && !bookDto.getReviewsId().isEmpty()) {
            List<Review> reviews = reviewRepository.findAllById(bookDto.getReviewsId());
            // Set the book reference in each review (important bi-directional relation fix)
            for (Review review : reviews) {
                review.setBook(book);
            }
            book.setReviews(reviews);
        }
        Book updated = bookRepository.save(book);
        return bookMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if(!bookRepository.existsById(id)){
            throw new EntityNotFoundException("Book with id: " + id + " not found");
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> searchByTitle(String title){
        String searchedTitle = "%" + title + "%"; //JPA doest not have typicall LIKE
       return bookRepository.findBooksByTitle(searchedTitle).stream().map(book -> bookMapper.toDto(book)).toList();
    }
}
