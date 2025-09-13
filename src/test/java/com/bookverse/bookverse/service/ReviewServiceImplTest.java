package com.bookverse.bookverse.service;

import com.bookverse.bookverse.dto.ReviewDto;
import com.bookverse.bookverse.mapper.ReviewMapper;
import com.bookverse.bookverse.model.Book;
import com.bookverse.bookverse.model.Review;
import com.bookverse.bookverse.model.User;
import com.bookverse.bookverse.repository.BookRepository;
import com.bookverse.bookverse.repository.ReviewRepository;
import com.bookverse.bookverse.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void create_ShouldSaveReview() {

        User user = new User();
        user.setId(1L);

        ReviewDto dto = new ReviewDto();
        dto.setBookId(1L);
        dto.setStars(5);
        dto.setContent("Great!");
        dto.setUserId(1L);

        Book book = new Book();
        book.setId(1L);

        Review entity = new Review();
        Review saved = new Review();
        ReviewDto savedDto = new ReviewDto();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(reviewMapper.toEntity(dto)).thenReturn(entity);
        when(reviewRepository.save(entity)).thenReturn(saved);
        when(reviewMapper.toDto(saved)).thenReturn(savedDto);

        ReviewDto result = reviewService.create(dto);

        assertEquals(savedDto, result);
        verify(reviewRepository).save(entity);
        verify(bookRepository).findById(1L);
    }

    @Test
    void getById_ShouldReturnDto() {
        Long id = 1L;
        Review review = new Review();
        ReviewDto dto = new ReviewDto();

        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));
        when(reviewMapper.toDto(review)).thenReturn(dto);

        ReviewDto result = reviewService.findById(id);

        assertEquals(dto, result);
        verify(reviewRepository).findById(id);
    }

    @Test
    void getById_ShouldThrow_WhenNotFound() {
        Long id = 1L;
        when(reviewRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewService.findById((id)));
    }

    @Test
    void delete_ShouldDelete_WhenExists() {
        Long id = 1L;

        when(reviewRepository.existsById(1L)).thenReturn(true);

        reviewService.delete(id);

        verify(reviewRepository,times(1)).deleteById(id);
    }

    @Test
    void delete_ShouldThrow_WhenNotExists() {
        Long id = 1L;
        when(reviewRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> reviewService.delete(id));
        verify(reviewRepository, times(0)).deleteById(id);
    }
}