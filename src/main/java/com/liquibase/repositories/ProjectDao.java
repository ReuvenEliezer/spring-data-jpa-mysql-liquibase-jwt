package com.liquibase.repositories;

import com.liquibase.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDao extends JpaRepository<Project, Long> {

}
