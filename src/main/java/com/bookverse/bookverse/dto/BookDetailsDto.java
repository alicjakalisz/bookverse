package com.bookverse.bookverse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data transfer object representing a book detail")
public class BookDetailsDto {

    @Schema(description = "Unique identifier of the book details", example = "1")
    private Long id;
    @Schema(description = "Image Url downloaded by Book's ISBN from Open Library API")
    private String imageUrl; // constructed from covers[0]
    @Size(max = 1000, message = "Description can't be longer than 1000 characters")
    @Schema(description = "Description")
    private String description;  // maybe use `by_statement`
    
    private String publishDate;
    private Long bookId;
}
