package com.bookverse.bookverse.service;

import com.bookverse.bookverse.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto findById(Long id);
    List<ReviewDto> findAll();
    ReviewDto create(ReviewDto reviewDto);
    ReviewDto update(Long id, ReviewDto reviewDto);
    void delete(Long id);
}
