package com.bookverse.bookverse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

//not all the fields
@Data
@Builder
@AllArgsConstructor
@Schema(description = "Data transfer object representing an author")
public class AuthorDto {

    @Schema(description = "Unique identifier of the author", example = "1")
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Name of the author", example = "Stephen King")
    private String name;

}
