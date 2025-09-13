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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookDetailsService bookDetailsService;

    @InjectMocks
    private BookServiceImpl bookService;


    @Test
    void create_ShouldMapSaveAndFetchDetailsSuccessfully() {
        // Given
        Long authorId = 1L;
        List<Long> reviewIds = List.of(10L, 20L);

        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test Book");
        bookDto.setAuthorId(authorId);
        bookDto.setIsbn("1234567890");
        bookDto.setReviewsId(reviewIds);

        Book bookEntity = new Book();
        bookEntity.setIsbn("1234567890");

        Book savedBook = new Book();
        savedBook.setId(100L);
        savedBook.setIsbn("1234567890");

        Author author = new Author();
        author.setId(authorId);

        Review review1 = new Review();
        Review review2 = new Review();

        BookDto expectedDto = new BookDto();
        expectedDto.setTitle("Test Book");

        // When
        when(bookMapper.toEntity(bookDto)).thenReturn(bookEntity);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(reviewRepository.findAllById(reviewIds)).thenReturn(List.of(review1, review2));
        when(bookRepository.save(bookEntity)).thenReturn(savedBook);
        when(bookMapper.toDto(savedBook)).thenReturn(expectedDto);

        // Act
        BookDto result = bookService.create(bookDto);

        // Then
        verify(bookMapper).toEntity(bookDto);
        verify(authorRepository).findById(authorId);
        verify(reviewRepository).findAllById(reviewIds);
        verify(bookRepository).save(bookEntity);
        verify(bookDetailsService).fetchAndSaveByIsbn("1234567890", 100L);
        verify(bookMapper).toDto(savedBook);

        assertEquals(expectedDto, result);
    }
    @Test
    void getById_ShouldReturnDto_WhenFound() {
        Long id = 1L;
        Book book = new Book();
        BookDto dto = new BookDto();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(dto);

        BookDto result = bookService.findById(id);

        assertEquals(dto, result);
        verify(bookRepository).findById(id);
    }

    @Test
    void getById_ShouldThrow_WhenNotFound() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.findById(id));
        verify(bookRepository).findById(id);
    }

    @Test
    void delete_ShouldCallDelete_WhenExists() {
        Long id = 1L;
        when(bookRepository.existsById(id)).thenReturn(true);

        bookService.delete(id);

        verify(bookRepository,Mockito.times(1)).deleteById(id);
    }

    @Test
    void delete_ShouldThrow_WhenNotExists() {
        Long id = 1L;
        when(bookRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> bookService.delete(id));
        verify(bookRepository, Mockito.times(0)).deleteById(id);
    }
}
