package Tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liquibase.LiquibaseApplication;
import com.liquibase.config.DbConnectionProp;
import com.liquibase.repositories.AuthorDao;
import com.liquibase.repositories.BookDao;
import com.liquibase.repositories.NoteDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@RunWith(SpringRunner.class)
//@PropertySource("classpath:db.properties")
@Testcontainers //TODO disable this row in order to running on a real mysql database
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = LiquibaseApplication.class)
public class AbstractTest {

    protected static final String localhost = "http://localhost:";
    @Container
    private static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"))
            .withDatabaseName("netapp")
            .withUsername("root")
            .withPassword("administrator")
            ;

    @DynamicPropertySource
    private static void mysQLContainer(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:netapp");// "jdbc:h2:file:C:/data/netapp");
//        registry.add("spring.datasource.driverClassName", () -> mySQLContainer.getDriverClassName());
        registry.add("spring.datasource.username", () -> mySQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> mySQLContainer.getPassword());
//        registry.add("spring.flyway.enabled", () -> "true");
    }

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

    @Value("${spring.server.port}")
    protected Integer serverPort;

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected NoteDao noteDao;
    @Autowired
    protected AuthorDao authorDao;
    @Autowired
    protected BookDao bookDao;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private DbConnectionProp dbConnectionProp;


    @Before
    public void setUp() {
//        truncateDatabase();
    }

    @Test
    @Ignore
    public void dropDataBase() {
        dropDatabase(true);
    }

    private void dropDatabase(boolean isDrop) {
        if (!isDrop) return;
        try (Connection conn = initConnection()) {
            String catalog = conn.getCatalog();
            executeSQL(conn, "DROP DATABASE IF EXISTS " + catalog);
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
