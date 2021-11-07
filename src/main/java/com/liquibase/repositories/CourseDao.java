package com.liquibase.repositories;

import com.liquibase.entities.Course;
import com.liquibase.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDao extends JpaRepository<Course, Long>  {
}
