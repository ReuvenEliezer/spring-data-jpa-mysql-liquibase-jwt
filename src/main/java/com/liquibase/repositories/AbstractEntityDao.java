package com.liquibase.repositories;

import com.liquibase.entities.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractEntityDao extends JpaRepository<AbstractEntity, Long> {
}
