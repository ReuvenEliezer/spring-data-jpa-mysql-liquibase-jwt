package Tests;

import com.liquibase.LiquibaseApplication;
import com.liquibase.config.DbConnectionProp;
import com.liquibase.repositories.AuthorDao;
import com.liquibase.repositories.BookDao;
import com.liquibase.repositories.NoteDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@RunWith(SpringRunner.class)
//@PropertySource("classpath:db.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = LiquibaseApplication.class)
public class AbstractTest {

//    @Value("${dbPassword}")
//    private String dbPassword;
//
//    @Value("${dbUserName}")
//    private String userName;
//
//    @Value("${jdbcdriver}")
//    private String jdbcdriver;
//
//    @Value("${connectionURL}")
//    private String connectionURL;


    @Autowired
    protected NoteDao noteDao;
    @Autowired
    protected AuthorDao authorDao;
    @Autowired
    protected BookDao bookDao;

    @Autowired
    private DbConnectionProp dbConnectionProp;

    private boolean isDrop = false;


    @Before
    public void setUp() {
        truncateDatabase();
    }

    @Test
    public void dropDataBase() {
        isDrop = true;
        dropDatabase();
    }

    private void dropDatabase() {
        if (!isDrop) return;
        try (Connection conn = initConnection()) {
            String catalog = conn.getCatalog();
            executeSQL(conn, "DROP DATABASE " + catalog);
            //        executeSQL("DROP DATABASE netapp");
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    private Connection initConnection() {
        try {
            return DriverManager.getConnection(dbConnectionProp.getUrl(), dbConnectionProp.getUser(), dbConnectionProp.getPassword());
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
        return null;
    }

    private void truncateDatabase() {
        try (Connection conn = initConnection()) {
            String catalog = conn.getCatalog();
            executeSQL(conn, "CALL " + catalog + ".TruncateTables");
            //        executeSQL("DROP DATABASE netapp");
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }


    private void executeSQL(Connection conn, String sql) {
        try (Statement stmt = conn.createStatement()) {
//            Class.forName(jdbcdriver);
            stmt.execute(sql);
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }
}
