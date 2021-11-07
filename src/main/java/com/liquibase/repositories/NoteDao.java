package com.liquibase.repositories;

import com.liquibase.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface NoteDao extends JpaRepository<Note, Long>  {
}
