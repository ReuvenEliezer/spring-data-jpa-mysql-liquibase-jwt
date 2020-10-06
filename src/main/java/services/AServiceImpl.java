package services;

import repositories.NoteDao;
import entities.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AServiceImpl implements ServiceApp {

    @Autowired
    private NoteDao noteDao;


    @Override
    public void start() {
        System.out.println("start AServiceImpl");
        Note note = new Note();
        note.setContent("Content");
        note.setTitle("title");
        Note save = noteDao.save(note);
    }
}
