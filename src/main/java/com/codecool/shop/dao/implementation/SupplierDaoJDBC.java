package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cickib on 2016.11.29..
 */
public class SupplierDaoJDBC implements SupplierDao {
    private static final String DBURL = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static int counter;

    private static SupplierDaoJDBC instance = null;

    public static SupplierDaoJDBC getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        supplier.setId(++counter);
        try (Connection connection = getConnection()) {
            PreparedStatement query = connection.prepareStatement("INSERT INTO supplier (name, description) VALUES (?, ?);");
            query.setString(1, supplier.getName());
            query.setString(2, supplier.getDescription());
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {

        String query = "SELECT * FROM supplier WHERE id ='" + id + "';";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                Supplier result = new Supplier(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                return result;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM supplier WHERE id = '" + id + "';";
        executeQuery(query);
    }

    @Override
    public List<Supplier> getAll() {
        String query = "SELECT * FROM supplier;";
        List<Supplier> resultList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                Supplier supplier = new Supplier(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                supplier.setId(resultSet.getInt("id"));
                supplier.setProducts(ProductDaoJDBC.getInstance().getBy(supplier));
                resultList.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DBURL,
                DB_USER,
                DB_PASSWORD);
    }

    private void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
        ) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
