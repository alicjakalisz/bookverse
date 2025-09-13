package com.bookverse.bookverse.mapper;

import com.bookverse.bookverse.dto.BookDto;
import com.bookverse.bookverse.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BookMapper implements BaseMapper<BookDto, Book> {

    @Autowired
    private BookDetailsMapper bookDetailsMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public BookDto toDto(Book entity) {
        //DTO is lighter so coverting to dto can be fully done in mappers
        if(entity == null) return null;
        return BookDto.builder().id(entity.getId())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .publicationYear(entity.getPublicationYear())
                .bookDetailsDto(bookDetailsMapper.toDto(entity.getBookDetails()))
                .authorId(entity.getAuthor().getId())
                .reviewsId(entity.getReviews()==null? new ArrayList<>():entity.getReviews().stream().map(r-> r.getId()).toList())
                .build();

    }

    @Override
    public Book toEntity(BookDto dto) {
        if(dto == null) return null;
        return Book.builder().id(dto.getId())
              //LEAVE IT FOR SERVICE and reached from DB  .author(authorMapper.toEntity(dto.getAuthor()))
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .publicationYear(dto.getPublicationYear())
                .bookDetails(bookDetailsMapper.toEntity(dto.getBookDetailsDto()))
              //LET IT FOR SERVICE and REACHED through DB  .reviews(dto.getReviews().stream().map(rdto -> reviewMapper.toEntity(rdto)).toList())
                .build();

        //relationships to be rebuilt in service layer
    }
}
