package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS test.users" +
                "(id bigint not null auto_increment, " +
                "name VARCHAR(50), " +
                "lastname VARCHAR(50), " +
                "age tinyint, " +
                "PRIMARY KEY (id))";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate(sql);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "Drop table if exists test.users";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate(sql);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM test.users WHERE id = " + id + " LIMIT 1 ";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate(sql);
            System.out.println("User удален");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList<>();
        String sql = "SELECT id, name, lastname, age from test.users";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUser.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUser;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM test.users";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate(sql);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очистить");
        }
    }
}