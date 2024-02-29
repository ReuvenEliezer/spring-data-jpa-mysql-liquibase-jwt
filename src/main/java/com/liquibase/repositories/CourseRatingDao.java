package com.liquibase.repositories;

import com.liquibase.entities.CourseRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRatingDao extends JpaRepository<CourseRating, Long> {
}
