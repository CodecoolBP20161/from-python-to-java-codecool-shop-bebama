package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.cart.User;
import com.codecool.shop.dao.UserDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.codecool.shop.HashClass.hasher;

public class UserDaoJDBC extends AbstractDaoJDBC implements UserDao{
    private static UserDaoJDBC instance = null;

    public static UserDaoJDBC getInstance() {
        if (instance == null) {
            instance = new UserDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(User user) throws Exception {
        try (Connection connection = getConnection()) {
            PreparedStatement query = connection.prepareStatement("INSERT INTO category (name, email, password, welcomeEmail) VALUES (?, ?, ?, ?);");
            query.setString(1, user.getName());
            query.setString(2, user.getEmail());
            query.setString(3, hasher(user.getPassword()));
            query.setBoolean(4, user.getWelcomeEmail());
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(int id) {
        User result = null;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE id ='" + id + "';");
        ) {
            if (rs.next()) {
                result = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getBoolean("welcomeEmail"));
                result.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User find(String name) {
        User result = null;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE NAME ='" + name + "';");
        ) {
            if (rs.next()) {
                result = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getBoolean("welcomeEmail"));
                result.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void remove(int id) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
        ) {
            statement.execute("DELETE FROM user WHERE id = '" + id + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        List<User> resultList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM user;");
        ) {
            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getBoolean("welcomeEmail"));
                user.setId(rs.getInt("id"));
                resultList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
