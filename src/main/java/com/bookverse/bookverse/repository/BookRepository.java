package com.bookverse.bookverse.repository;

import com.bookverse.bookverse.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    @Query("SELECT b FROM Book b WHERE b.title LIKE :title")
    List<Book> findBooksByTitle(@Param("title") String title);

    @Query(value = "SELECT * FROM books WHERE rating > 4", nativeQuery = true)
    List<Book> findTopRatedBooks();
}
