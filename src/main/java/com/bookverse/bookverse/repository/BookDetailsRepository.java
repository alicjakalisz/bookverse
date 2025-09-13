package com.bookverse.bookverse.repository;

import com.bookverse.bookverse.model.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails,Long> {
}
