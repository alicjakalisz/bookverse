package com.bookverse.bookverse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private Long id;
    @NotBlank(message = "Username is required")
    private String username;

}