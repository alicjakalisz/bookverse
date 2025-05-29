package com.bookverse.bookverse.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoverMetaDataDto {
    private Long id;
    private String imageUrl;
    private String description;
}
