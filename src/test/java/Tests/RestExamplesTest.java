package Tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liquibase.entities.Author;
import com.liquibase.entities.Book;
import com.liquibase.entities.Case;
import com.liquibase.entities.Note;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;


public class RestExamplesTest extends AbstractTest {

    @Autowired
    private ObjectMapper objectMapper;

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
    public void timeRestTest() {
        restTemplate.getForObject(localhost + serverPort + "/times/printLocalDateTime/" + LocalDateTime.now(), Void.class);
    }

    @Test
    public void durationRestTest() {
        restTemplate.getForObject(localhost + serverPort + "/times/printDuration/" + Duration.ofHours(20), Void.class);
    }

    @Test
    public void timeObjectMapperTest() throws JsonProcessingException {
        TimeStampDateEntity timeStampDateEntity = objectMapper.readValue("{\"timestamp1\":\"May 27, 2021 4:21:03 PM\",\"timestamp2\":\"May 15, 2019 4:10:03AM\",\"date\":\"May 27, 2021\"}", TimeStampDateEntity.class);
        String s = objectMapper.writeValueAsString(timeStampDateEntity);
        TimeStampDateEntity timeStampDateEntity1 = objectMapper.readValue(s, TimeStampDateEntity.class);
    }

    @Data
    @NoArgsConstructor  //for Serializable
    private static class TimeStampDateEntity implements Serializable {
        private Timestamp timestamp1;
        private Timestamp timestamp2;
        private Date date;
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
