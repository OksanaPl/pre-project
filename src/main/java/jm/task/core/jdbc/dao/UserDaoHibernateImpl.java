package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        SessionFactory factory = Util.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.getCurrentSession()) {
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "( id INT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(45), lastName VARCHAR(45), " +
                    "age INT(3), PRIMARY KEY ( id ))";
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.getMessage();
        }
    }

    @Override
    public void dropUsersTable() {
        SessionFactory factory = Util.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.getCurrentSession()) {
            String sql = "DROP TABLE IF EXISTS users";
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.getMessage();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory factory = Util.getSessionFactory();
        Transaction transaction = null;
        try  (Session session = factory.getCurrentSession()){
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.getMessage();
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory factory = Util.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.getMessage();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        SessionFactory factory = Util.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("FROM User ", User.class).list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.getMessage();
        }
        result.forEach(r -> System.out.println(r.toString()));
        return result;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory factory = Util.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.getCurrentSession()) {
            String sql = "DELETE FROM users";
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
//            List<User> result = getAllUsers();
//            result.forEach(session::delete);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.getMessage();
        }
    }

}
