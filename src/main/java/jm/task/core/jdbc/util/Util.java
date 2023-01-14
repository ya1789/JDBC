package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String url = "jdbc:mysql://localhost:3306/usersdb";
    private static final String user = "root";
    private static final String password = "htg1165";
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
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
