package Tests;

import com.liquibase.LiquibaseApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(SpringRunner.class)
@PropertySource("classpath:db.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = LiquibaseApplication.class)
public abstract class AbstractTest {

    @Value("${dbPassword}")
    private String dbPassword;

    @Value("${userName}")
    private String userName;

    @Value("${jdbcdriver}")
    private String jdbcdriver;

    @Value("${connectionURL}")
    private String connectionURL;


    @Before
    public void setUp(){
        truncateDatabase();
    }

    public void truncateDatabase(){
//        executeSQL("CALL netapp.TruncateTables");
//        executeSQL("DROP DATABASE netapp");
    }


    private void executeSQL(String sql) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(jdbcdriver);
            conn = DriverManager.getConnection(connectionURL, userName, dbPassword);
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
