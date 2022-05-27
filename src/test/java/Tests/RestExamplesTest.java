package Tests;

import com.liquibase.entities.Author;
import com.liquibase.entities.Book;
import com.liquibase.entities.Note;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;


public class RestExamplesTest extends AbstractTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void PostTest() throws InterruptedException {
        Note note = new Note();
        note.setContent("Content");
        note.setTitle("title");
        Note save = noteDao.save(note);
        Thread.sleep(2000); // simulated work
        save = noteDao.save(note);
//        restTemplate.postForObject(WsAddressConstants.restExamplesFullUrl + "post", "ss", Void.class);
    }


    @Test
    public void BookTest() {
        String authorName = "authorName";
        Author author = authorDao.getByName(authorName);
        if (author == null) {
            author = authorDao.save(new Author(authorName));
        }

        String book1Name = "bookName1";
        Book book1 = bookDao.getByName(book1Name);
        if (book1 == null) {
            book1 = bookDao.save(new Book(book1Name, author));
        }

        String book2Name = "bookName2";
        Book book2 = bookDao.getByName(book2Name);
        if (book2 == null) {
            book2 = bookDao.save(new Book(book2Name, author));
        }

    }

}
