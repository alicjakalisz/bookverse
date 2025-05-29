package com.bookverse.bookverse.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



@Entity
@Builder
@Getter
@Setter
public class CoverMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String imageUrl;

    @OneToOne(mappedBy = "coverMetaData")
    private Book book;
}
