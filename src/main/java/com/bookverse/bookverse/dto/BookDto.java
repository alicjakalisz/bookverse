package com.bookverse.bookverse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;

    private String title;

    private String isbn;

    private int publicationYear;

    private Long authorId;

    private BookDetailsDto bookDetailsDto;

    private List<Long> reviewsId;


}
