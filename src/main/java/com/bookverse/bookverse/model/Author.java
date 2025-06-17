package com.bookverse.bookverse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.List;


@Builder
@Entity
@Data
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL) // Author is owned by Books so if you remove certain books from Books
    //table this list will change (wont have these books)
    private List<Book> books;



}
