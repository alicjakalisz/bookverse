package com.bookverse.bookverse.mapper;

import com.bookverse.bookverse.dto.BookDetailsDto;
import com.bookverse.bookverse.model.BookDetails;
import org.springframework.stereotype.Component;

@Component
public class BookDetailsMapper implements BaseMapper<BookDetailsDto, BookDetails> {
    @Override
    public BookDetailsDto toDto(BookDetails entity) {
        if(entity == null) return null;
        return BookDetailsDto.builder().id(entity.getId())
                .imageUrl(entity.getImageUrl())
                .description(entity.getDescription())
                .publishDate(entity.getPublishDate())
                .bookId(entity.getBook().getId())
                .build();

    }

    @Override
    public BookDetails toEntity(BookDetailsDto dto) {
        if(dto == null) return null;
        return BookDetails.builder().id(dto.getId())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .publishDate(dto.getPublishDate()).build();
        //we are leaving book as we will add it in the servie layer
    }
}
