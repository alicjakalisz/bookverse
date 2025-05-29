package com.bookverse.bookverse.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDto {
    private Long id;
    private String content;
    private Integer stars;
    private Long bookId;
    private Long userId;
}