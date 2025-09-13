package com.bookverse.bookverse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object representing a review")
public class ReviewDto {

    @Schema(description = "Unique identifier of the review", example = "1")
    private Long id;
   // @NotBlank(message = "Review content is required")
   // @Size(min = 5, max = 500, message = "Review must be between 5 and 500 characters")
    private String content;

//    @Min(value = 1, message = "Rating must be at least 1")
//    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer stars;
    private Long bookId;
    private Long userId;
}