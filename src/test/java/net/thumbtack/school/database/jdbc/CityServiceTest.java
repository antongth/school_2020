package net.thumbtack.school.database.jdbc;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class CityServiceTest {
    private static Connection connection;
    private static final String USER = "test";
    private static final String PASSWORD = "test";
    private static final String URL = "jdbc:mysql://localhost:3306/ttschool?useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Omsk";

    private static boolean setUpIsDone = false;

    @BeforeAll
    public static void setUp() {
        if (!setUpIsDone) {
            boolean createConnection = JdbcUtils.createConnection();
            if (!createConnection) {
                throw new RuntimeException("Can't create connection, stop");
            }
            setUpIsDone = true;
        }
    }

    @AfterAll
    public static void close() {
        if (setUpIsDone)
            JdbcUtils.closeConnection();
    }

    @Test
    public void test() throws SQLException, IOException {
        CityService cityService = new CityService();
        List<String> infoOnCites = cityService.tryToTestMe();
        Assert.assertEquals(3, infoOnCites.size());
        Assert.assertTrue(infoOnCites.get(0).contains("Paris"));
        Assert.assertTrue(infoOnCites.get(0).contains("France"));
        Assert.assertTrue(infoOnCites.get(0).contains("Euro"));
    }
}
