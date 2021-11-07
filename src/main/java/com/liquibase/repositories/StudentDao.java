package com.liquibase.repositories;

import com.liquibase.entities.Course;
import com.liquibase.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<Student, Long>  {
}
