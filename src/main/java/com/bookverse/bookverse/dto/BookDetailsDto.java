package com.bookverse.bookverse.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsDto {
    private Long id;
    private String imageUrl; // constructed from covers[0]
    @Size(max = 1000, message = "Description can't be longer than 1000 characters")
    private String description;  // maybe use `by_statement`
    private String publishDate;
    private Long bookId;
}
