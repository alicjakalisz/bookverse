package com.bookverse.bookverse.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
