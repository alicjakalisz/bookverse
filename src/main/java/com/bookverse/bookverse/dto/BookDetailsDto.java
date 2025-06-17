package com.bookverse.bookverse.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDetailsDto {
    private Long id;
    private String imageUrl; // constructed from covers[0]
    private String description;  // maybe use `by_statement`
    private String publishDate;
    private Long bookId;
}
