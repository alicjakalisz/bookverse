package com.bookverse.bookverse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "Data transfer object representing a user")
public class UserDto {

    @Schema(description = "Unique ID of the user", example = "1")
    private Long id;
    @NotBlank(message = "Username is required")
    @Schema(description = "Full name of the user", example = "John Doe", required = true)
    private String username;

}