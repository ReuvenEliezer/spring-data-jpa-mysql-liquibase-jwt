package com.liquibase.repositories;

import com.liquibase.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileDao extends JpaRepository<Profile, Long> {
}
