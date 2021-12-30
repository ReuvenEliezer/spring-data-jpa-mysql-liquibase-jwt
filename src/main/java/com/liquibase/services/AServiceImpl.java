package com.liquibase.services;

import com.liquibase.entities.AbstractEntity;
import com.liquibase.entities.Note;
import com.liquibase.repositories.AbstractEntityDao;
import com.liquibase.repositories.NoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AServiceImpl implements ServiceApp {

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private AbstractEntityDao abstractEntityDao;


    @Override
    public void start() {
        List<AbstractEntity> all = abstractEntityDao.findAll();
        for (AbstractEntity entity : all) {
            logger.debug(entity.toString());
        }
        logger.debug("start AServiceImpl");
        Note note = new Note();
        note.setContent("Content");
        note.setTitle("title");
        Note save = noteDao.save(note);
    }
}
