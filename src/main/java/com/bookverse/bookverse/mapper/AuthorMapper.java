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
        //Your mapper builds basic entity structure from DTO (Dto has AuthorId, not the entire Author, you need to make a call to database from service to fetch the author by id and assign it to the entity
        //The service layer should handle fetching relationships from the database
        //This keeps responsibilities clear and avoids partial/inconsistent objects
        return Author.builder().id(dto.getId()).name(dto.getName()).build();
    }
}
