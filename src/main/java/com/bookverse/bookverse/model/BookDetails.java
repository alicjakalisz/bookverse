package com.bookverse.bookverse.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



@Entity
@Builder
@Getter
@Setter
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String imageUrl;

    private String publishDate;

    @OneToOne(mappedBy = "bookDetails") //mappedBy == NOT owning side; Book owns details
    private Book book;
}
