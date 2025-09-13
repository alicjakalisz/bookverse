package com.bookverse.bookverse.mapper;

import com.bookverse.bookverse.dto.AuthorDto;
import com.bookverse.bookverse.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements BaseMapper<AuthorDto, Author> {
    @Override
    public AuthorDto toDto(Author author) {
        if (author == null) return null;

        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }

    @Override
    public Author toEntity(AuthorDto dto) {
        if(dto == null) return null;
       //List of Authors books need to be taken from DB at the Service layer
        return Author.builder().id(dto.getId()).name(dto.getName()).build();
    }
}
