package com.bookverse.bookverse.repository;

import com.bookverse.bookverse.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
//    @Query("SELECT a from Author a WHERE a.id =:id")
//    Optional<Author> getAuthorById(@Param("id") Long id);


}
