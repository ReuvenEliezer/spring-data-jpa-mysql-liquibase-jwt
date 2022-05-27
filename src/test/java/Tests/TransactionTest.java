package Tests;

import com.liquibase.entities.Author;
import com.liquibase.entities.Book;
import com.liquibase.services.transactional.TransactionalOperationsUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class TransactionTest extends AbstractTest {


    @Autowired
    private TransactionalOperationsUtil transactionalOperationsUtil;

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
        long bookSize = bookDao.count();
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
            Assert.assertEquals(bookSize, bookDao.count());
            return;
        }
        Assert.fail();
    }

}
