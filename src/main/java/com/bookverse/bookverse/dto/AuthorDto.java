package com.bookverse.bookverse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

//not all the fields
@Data
@Builder
@AllArgsConstructor
public class AuthorDto {
    private Long id;
    private String name;

}
