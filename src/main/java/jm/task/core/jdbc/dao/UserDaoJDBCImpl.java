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
    public static final String TABLECREATION = "CREATE TABLE IF NOT EXISTS users (id INTEGER not NULL UNIQUE AUTO_INCREMENT,  name VARCHAR(255),  lastName VARCHAR(255),  age INTEGER );";
    public static final String TABLEREMOVAL = "DROP TABLE IF EXISTS users;";
    public static final String ADDUSERTOTABLE = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    public static final String DELETEUSERBYID = "DELETE FROM users WHERE id=?";
    public static final String ALLRECORDS = "SELECT * FROM users";
    public static final String TABLECLEARING = "DELETE FROM users";

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(TABLECREATION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(TABLEREMOVAL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement stmt = connection.prepareStatement(ADDUSERTOTABLE)) {
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
        try (PreparedStatement stmt = connection.prepareStatement(DELETEUSERBYID)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(ALLRECORDS)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                allUsers.add(
                        new User(
                                resultSet.getString("name"),
                                resultSet.getString("lastName"),
                                resultSet.getByte("age")
                        ));
            }
            allUsers.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(TABLECLEARING);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
