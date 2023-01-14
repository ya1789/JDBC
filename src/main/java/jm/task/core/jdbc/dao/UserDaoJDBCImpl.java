package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getInstance().getConnection();
    }

    public void createUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            String sqlStmt = "CREATE TABLE users (id INTEGER not NULL UNIQUE AUTO_INCREMENT,  name VARCHAR(255),  lastName VARCHAR(255),  age INTEGER );";
            stmt.execute(sqlStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            String sqlStmt = "DROP TABLE IF EXISTS users;";
            stmt.execute(sqlStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlStmt = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sqlStmt)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
            System.out.printf("User с именем %s добавлен в базу данных%n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try (Statement stmt = connection.createStatement()) {
            String sqlStmt = "DELETE FROM users WHERE id=%d".formatted(id);
            stmt.execute(sqlStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        String sqlStmt = "SELECT * FROM users";
        ArrayList<User> allUsers = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sqlStmt)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                allUsers.add(user);
            }
            allUsers.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }

    public void cleanUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            String sqlStmt = "DELETE FROM users";
            stmt.execute(sqlStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
