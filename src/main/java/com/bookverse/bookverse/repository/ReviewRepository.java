package com.bookverse.bookverse.repository;

import com.bookverse.bookverse.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {



     @Query("SELECT r FROM Review r JOIN r.user u WHERE u.name = :username")
    List<Review> findAllByUserName(@Param("username") String username);
}
