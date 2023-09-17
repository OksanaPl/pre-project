package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "( id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45), lastName VARCHAR(45), " +
                "age INT(3), PRIMARY KEY ( id ))";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sql);
            System.out.println("Таблица users удалена!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?,?,?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("user с id = " + id + " удален");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try (Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sql);
            System.out.println("Таблица users очищена!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
