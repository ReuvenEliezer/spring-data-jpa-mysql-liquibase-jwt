package Tests;

import com.liquibase.LiquibaseApplication;
import com.liquibase.entities.Author;
import com.liquibase.entities.Book;
import org.junit.Assert;
import com.liquibase.repositories.AuthorDao;
import com.liquibase.repositories.BookDao;
import com.liquibase.repositories.NoteDao;
import com.liquibase.entities.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import com.liquibase.services.transactional.TransactionalOperationsUtil;

import java.util.UUID;


public class RestExamplesTest extends AbstractTest {

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionalOperationsUtil transactionalOperationsUtil;

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
    public void transactionalSuccessTest() {
        String book1Name = "bookName " + UUID.randomUUID();
        try {
            transactionalOperationsUtil.invokeTransactional(() -> {
                String authorName = "authorName";
                Author author = authorDao.getByName(authorName);
                if (author == null) {
                    author = authorDao.save(new Author(authorName));
                }
                return bookDao.save(new Book(book1Name, author));
            });
        } catch (Exception e) {
            Assert.fail();
        }

        Assert.assertNotNull(bookDao.getByName(book1Name));
    }

    @Test
    public void transactionalFailedTest() {
        String book1Name = "bookName " + UUID.randomUUID();
        try {
            transactionalOperationsUtil.invokeTransactional(() -> {
                String authorName = "authorName";
                Author author = authorDao.getByName(authorName);
                if (author == null) {
                    author = authorDao.save(new Author(authorName));
                }
                bookDao.save(new Book(book1Name, author));

                throw new RuntimeException();
            });
        } catch (Exception e) {
            Assert.assertNull(bookDao.getByName(book1Name));
            return;
        }
        Assert.fail();
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
