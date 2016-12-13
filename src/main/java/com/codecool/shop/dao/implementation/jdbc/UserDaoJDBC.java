package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.HashClass;
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
            PreparedStatement query = connection.prepareStatement("INSERT INTO usertable (u_name, email, password, welcomeEmail) VALUES (?, ?, ?, ?);");
            query.setString(1, user.getName());
            query.setString(2, user.getEmail());
            query.setString(3, hasher(user.getPassword()));
            int emailStatus = (user.getWelcomeEmail()) ? 1 : 0;
            query.setInt(4, emailStatus);
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
             ResultSet rs = statement.executeQuery("SELECT * FROM usertable WHERE u_id ='" + id + "';");
        ) {
            if (rs.next()) {
                boolean emailStatus = rs.getInt("welcomeEmail") == 1;
                result = new User(rs.getString("u_name"), rs.getString("email"), rs.getString("password"), emailStatus);
                result.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User findEmail(String email) {
        User result = null;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM usertable WHERE email ='" + email + "';");
        ) {
            if (rs.next()) {
                boolean emailStatus = rs.getInt("welcomeEmail") == 1;
                result = new User(rs.getString("u_name"), rs.getString("email"), rs.getString("password"), emailStatus);
                result.setId(rs.getInt("u_id"));
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
             ResultSet rs = statement.executeQuery("SELECT * FROM usertable WHERE u_name ='" + name + "';");
        ) {
            if (rs.next()) {
                boolean emailStatus = rs.getInt("welcomeEmail") == 1;
                result = new User(rs.getString("u_name"), rs.getString("email"), rs.getString("password"), emailStatus);
                result.setId(rs.getInt("u_id"));
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
            statement.execute("DELETE FROM usertable WHERE u_id = '" + id + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        List<User> resultList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM usertable;");
        ) {
            while (rs.next()) {
                boolean emailStatus = rs.getInt("welcomeEmail") == 1;
                User user = new User(rs.getString("u_name"), rs.getString("email"), rs.getString("password"), emailStatus);
                user.setId(rs.getInt("u_id"));
                resultList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
