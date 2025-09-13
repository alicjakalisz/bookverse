package com.bookverse.bookverse.mapper;

import com.bookverse.bookverse.dto.ReviewDto;
import com.bookverse.bookverse.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper implements BaseMapper<ReviewDto, Review> {
    @Override
    public ReviewDto toDto(Review entity) {
        if(entity == null) return null;
        return ReviewDto.builder().id(entity.getId())
                .stars(entity.getRating())
                .content(entity.getContent())
                .bookId(entity.getBook().getId())
                .userId(entity.getUser().getId()).build();
    }

    @Override
    public Review toEntity(ReviewDto dto) {
        if(dto == null) return null;
        return Review.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .rating(dto.getStars()).build();
        //User, Book to be added in Service layer
    }
}
