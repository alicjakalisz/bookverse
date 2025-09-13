package com.bookverse.bookverse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Data transfer object representing a book")
public class BookDto {

    @Schema(description = "Unique identifier of the book", example = "1")
    private Long id;

    @NotBlank(message = "Book title is required")
    @Size(min = 1, max = 150, message = "Title must be between 1 and 150 characters")
    @Schema(description = "Title of the book", required = true)
    private String title;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 13, message = "ISBN must be 10 or 13 characters long")
    @Schema(description = "ISBN that exists in OpenLibrary API", required = true)
    private String isbn;

    private int publicationYear;

    @NotNull(message = "Author ID is required")
    @Schema(description = "Author owns the book so needs to be created first in db", required = true)
    private Long authorId;

    @Schema(description = "Populated from OpenLibrary API providing the ISBN exists")
    private BookDetailsDto bookDetailsDto;

    @Schema(description = "List of review ids of these book")
    private List<Long> reviewsId;
}
