package com.liquibase.repositories;

import com.liquibase.entities.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CaseDao extends JpaRepository<Case, Long> {

    @Query("select u from #{#entityName} u where u.id = ?1 and u.isDeleted = false")
    Case findNonDeleted(Long id);

    @Query("select u from #{#entityName} u where u.isDeleted = false")
    List<Case> findAllNonDeleted();
}
