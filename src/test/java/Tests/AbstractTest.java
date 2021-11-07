package Tests;

import com.liquibase.LiquibaseApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
public class AbstractTest {

    @Value("${dbPassword}")
    private String dbPassword;

    @Value("${dbUserName}")
    private String userName;

    @Value("${jdbcdriver}")
    private String jdbcdriver;

    @Value("${connectionURL}")
    private String connectionURL;


    private boolean isDrop = false;


    @Before
    public void setUp() {
        Connection conn = initConnection();
        truncateDatabase(conn);
    }

    @Test
    public void dropDataBase() {
        Connection conn = initConnection();
        isDrop = true;
        dropDatabase(conn);
    }

    private void dropDatabase(Connection conn) {
        if (!isDrop) return;
        try {
            String catalog = conn.getCatalog();
            executeSQL(conn, "DROP DATABASE " + catalog);
            //        executeSQL("DROP DATABASE netapp");
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    private Connection initConnection() {
        try {
            return DriverManager.getConnection(connectionURL, userName, dbPassword);
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
        return null;
    }

    private void truncateDatabase(Connection conn) {
        try {
            String catalog = conn.getCatalog();
            executeSQL(conn, "CALL " + catalog + ".TruncateTables");
            //        executeSQL("DROP DATABASE netapp");
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }


    private void executeSQL(Connection conn, String sql) {
        Statement stmt = null;
        try {
//            Class.forName(jdbcdriver);
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            Assert.fail(e.toString());
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
