package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();
    public static final String TABLECREATION = "CREATE TABLE IF NOT EXISTS users (id INTEGER not NULL UNIQUE AUTO_INCREMENT,  name VARCHAR(255),  lastName VARCHAR(255),  age INTEGER );";
    public static final String TABLEREMOVAL = "DROP TABLE IF EXISTS users;";
    public static final String TABLECLEARING = "DELETE FROM User";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery(TABLECREATION).addEntity(User.class);
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
            Query query = session.createSQLQuery(TABLEREMOVAL).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.save(user);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // Getting Session Object From SessionFactory
            // Getting Transaction Object From Session Object
            transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            // Committing The Transactions To The Database
            transaction.commit();
        } catch (Exception sqlException) {
            if (transaction != null) {
                transaction.rollback();
            }
            sqlException.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List allUsers = new ArrayList<User>();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            allUsers = session.createQuery("FROM User").list();
            transaction.commit();
        } catch (Exception sqlException) {
            if (transaction != null) {
                transaction.rollback();
            }
            sqlException.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // Getting Session Object From SessionFactory
            // Getting Transaction Object From Session Object
            transaction = session.beginTransaction();
            Query query = session.createQuery(TABLECLEARING);
            query.executeUpdate();
            // Committing The Transactions To The Database
            transaction.commit();
        } catch (Exception sqlException) {
            if (transaction != null) {
                transaction.rollback();
            }
            sqlException.printStackTrace();
        }
    }
}
