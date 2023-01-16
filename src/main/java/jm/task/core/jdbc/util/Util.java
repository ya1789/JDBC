package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static Connection connection;

    private Util() {

    }

    private static class UtilHolder {
        public static final Util UTIL_INSTANCE = new Util();
    }

    public static Util getInstance() {
        return UtilHolder.UTIL_INSTANCE;
    }

    public static Connection getConnection() {
        Properties properties = getProps();
        try {
            connection = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"),properties.getProperty("db.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Properties getProps() {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("src/main/resources/app.properities")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
