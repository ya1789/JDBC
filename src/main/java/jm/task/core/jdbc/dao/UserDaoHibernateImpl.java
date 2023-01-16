package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.SessionFactory;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sqlStmt = "CREATE TABLE users (id INTEGER not NULL UNIQUE AUTO_INCREMENT,  name VARCHAR(255),  lastName VARCHAR(255),  age INTEGER );";
            Query query = session.createSQLQuery(sqlStmt).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sqlStmt = "DROP TABLE IF EXISTS users;";
            Query query = session.createSQLQuery(sqlStmt).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()) {
            // start a transaction
            session.beginTransaction();
            // save the student object
            session.save(user);
            // commit transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            // Getting Session Object From SessionFactory
            // Getting Transaction Object From Session Object
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            // Committing The Transactions To The Database
            session.getTransaction().commit();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List allUsers = new ArrayList<User>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            allUsers = session.createQuery("FROM User").list();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            // Getting Session Object From SessionFactory
            // Getting Transaction Object From Session Object
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            // Committing The Transactions To The Database
            session.getTransaction().commit();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }
}
