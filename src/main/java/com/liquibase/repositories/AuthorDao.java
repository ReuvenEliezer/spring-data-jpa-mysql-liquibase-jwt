package com.liquibase.repositories;

import com.liquibase.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorDao extends JpaRepository<Author, Long> {

    //    @Query("SELECT u FROM Author u WHERE u.name = ?1")
    @Query("select u from #{#entityName} u where u.name = ?1")
    Author getByName(String authorName);

}
