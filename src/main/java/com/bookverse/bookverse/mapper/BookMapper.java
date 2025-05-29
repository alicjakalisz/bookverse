package com.bookverse.bookverse.mapper;

import com.bookverse.bookverse.dto.BookDto;
import com.bookverse.bookverse.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements BaseMapper<BookDto, Book> {

    @Autowired
    private CoverMetaDataMapper coverMetaDataMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public BookDto toDto(Book entity) {
        if(entity == null) return null;
        return BookDto.builder().id(entity.getId())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .publicationYear(entity.getPublicationYear())
                .coverMetaDataDto(coverMetaDataMapper.toDto(entity.getCoverMetaData()))
                .author(authorMapper.toDto(entity.getAuthor()))
                .reviews(entity.getReviews().stream().map(r-> reviewMapper.toDto(r)).toList())
                .build();

    }

    @Override
    public Book toEntity(BookDto dto) {
        if(dto == null) return null;
        return Book.builder().id(dto.getId())
              //  .author(authorMapper.toEntity(dto.getAuthor()))
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .publicationYear(dto.getPublicationYear())
                .coverMetaData(coverMetaDataMapper.toEntity(dto.getCoverMetaDataDto()))
              //  .reviews(dto.getReviews().stream().map(rdto -> reviewMapper.toEntity(rdto)).toList())
                .build();

        //relationships to be rebuilt in service layer
    }
}
