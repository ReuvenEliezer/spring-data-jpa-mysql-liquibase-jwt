package com.liquibase.repositories;

import com.liquibase.entities.Case;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseDao extends JpaRepository<Case, Long> {
}
