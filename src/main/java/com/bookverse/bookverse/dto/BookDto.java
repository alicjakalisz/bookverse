package com.bookverse.bookverse.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class BookDto {
    private Long id;

    private String title;

    private String isbn;

    private int publicationYear;

    private AuthorDto author;

    private List<ReviewDto> reviews;

    private CoverMetaDataDto coverMetaDataDto;
}
