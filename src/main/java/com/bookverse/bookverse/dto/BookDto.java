package com.bookverse.bookverse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Book title is required")
    @Size(min = 1, max = 150, message = "Title must be between 1 and 150 characters")
    private String title;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 13, message = "ISBN must be 10 or 13 characters long")
    private String isbn;

    private int publicationYear;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    private BookDetailsDto bookDetailsDto;

    private List<Long> reviewsId;
}
