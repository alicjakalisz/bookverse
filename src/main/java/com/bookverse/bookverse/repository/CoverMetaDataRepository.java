package com.bookverse.bookverse.repository;

import com.bookverse.bookverse.model.CoverMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverMetaDataRepository extends JpaRepository<CoverMetaData,Long> {
}
