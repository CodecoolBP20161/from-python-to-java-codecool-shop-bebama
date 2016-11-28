package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makaimark on 2016.11.28..
 */
public class ProductCategoryDaoJDBC implements ProductCategoryDao {
    private static final String DBURL = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    private static ProductCategoryDaoJDBC instance = null;

    public static ProductCategoryDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        String query = "INSERT INTO category (id, name, description, department) " +
                "VALUES ('" + category.getId() + "', '" + category.getName() + "', '" + category.getDescription() + "', '" + category.getDepartment() + "');";
        executeQuery(query);
    }

    @Override
    public ProductCategory find(int id) {

        String query = "SELECT * FROM category WHERE id ='" + id + "';";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                ProductCategory result = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("department"));
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
        String query = "DELETE FROM category WHERE id = '" + id + "';";
        executeQuery(query);
    }

    @Override
    public List<ProductCategory> getAll() {
        String query = "SELECT * FROM category;";

        List<ProductCategory> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                ProductCategory category = new ProductCategory(resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("department"));
                resultList.add(category);
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
