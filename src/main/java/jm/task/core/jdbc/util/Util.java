package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static Connection connection;
    private static SessionFactory sessionFactory;

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
            connection = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
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

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/usersdb");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "htg1165");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.out.println("Problem creating session factory");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

}
