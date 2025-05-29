package com.bookverse.bookverse.mapper;

import com.bookverse.bookverse.dto.CoverMetaDataDto;
import com.bookverse.bookverse.model.CoverMetaData;
import org.springframework.stereotype.Component;

@Component
public class CoverMetaDataMapper implements BaseMapper<CoverMetaDataDto, CoverMetaData> {
    @Override
    public CoverMetaDataDto toDto(CoverMetaData entity) {
        if(entity == null) return null;
        return CoverMetaDataDto.builder().id(entity.getId())
                .imageUrl(entity.getImageUrl())
                .description(entity.getDescription()).build();
    }

    @Override
    public CoverMetaData toEntity(CoverMetaDataDto dto) {
        if(dto == null) return null;
        return CoverMetaData.builder().id(dto.getId())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl()).build();
        //we are leaving book as we will add it in the servie layer
    }
}
