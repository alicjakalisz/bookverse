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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;
    private ReviewMapper reviewMapper;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }



    @Override
    public ReviewDto findById(Long id) {
       return reviewRepository.findById(id).map(entity -> reviewMapper.toDto(entity))
                .orElseThrow(()-> new EntityNotFoundException("Review with id: " + " not found"));
    }

    @Override
    public List<ReviewDto> findAll() {
        return reviewRepository.findAll().stream().map(review -> reviewMapper.toDto(review)).toList();
    }

    @Override
    public ReviewDto create(ReviewDto reviewDto) {
        Review entity = reviewMapper.toEntity(reviewDto);
        // add user, book from db to review entity
        Book bookFound = bookRepository.findById(reviewDto.getBookId()).orElseThrow(() -> new EntityNotFoundException("Book for this review not found"));
        User user = userRepository.findById(reviewDto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User of this review not found"));
        entity.setBook(bookFound);
        entity.setUser(user);
        Review saved = reviewRepository.save(entity);
        return reviewMapper.toDto(saved);
    }

    @Override
    public ReviewDto update(Long id, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review to be updated not found"));
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getStars());
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
