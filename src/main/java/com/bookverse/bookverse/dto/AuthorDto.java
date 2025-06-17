package com.bookverse.bookverse.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

//not all the fields
@Data
@Builder
public class AuthorDto {
    private Long id;
    private String name;

}
