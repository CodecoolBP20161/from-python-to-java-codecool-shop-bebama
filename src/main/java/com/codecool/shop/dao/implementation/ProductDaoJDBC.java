package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.*;
import com.codecool.shop.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makaimark on 2016.11.29..
 */
public class ProductDaoJDBC implements ProductDao {
    private static final String DBURL = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static int counter;

    private static ProductDaoJDBC instance = null;

    public static ProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        product.setId(++counter);
        try (Connection connection = getConnection()) {
            PreparedStatement query = connection.prepareStatement("INSERT INTO product (name, description, defaultPrice, defaultCurrency, categoryId, supplierId) VALUES (?, ?, ?, ?, ?, ?);");
            query.setString(1, product.getName());
            query.setString(2, product.getDescription());
            String price = product.getPrice().replaceAll(product.getDefaultCurrency().toString(), "");
            query.setString(3, price);
            query.setString(4, String.valueOf(product.getDefaultCurrency()));
            query.setInt(5, product.getProductCategory().getId());
            query.setInt(6, product.getSupplier().getId());
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        String query = "SELECT * FROM product WHERE id ='" + id + "';";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                ProductCategoryDao category = ProductCategoryDaoJDBC.getInstance();
                ProductCategory category1 = category.find(Integer.parseInt(resultSet.getString("categoryId")));
                SupplierDao supplier = SupplierDaoJDBC.getInstance();
                Supplier supplier1 = supplier.find(Integer.parseInt(resultSet.getString("supplierId")));
                Float defaultPrice = Float.parseFloat(resultSet.getString("defaultPrice").replaceAll(resultSet.getString("defaultCurrency"), ""));
                Product result = new Product(
                        resultSet.getString("name"),
                        defaultPrice,
                        resultSet.getString("defaultCurrency"),
                        resultSet.getString("description"),
                        category1, supplier1);
                result.setId(id);
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
        String query = "DELETE FROM product WHERE id = '" + id + "';";
        executeQuery(query);
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT id FROM product;";
        List<Product> resultList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                Product product = find(resultSet.getInt("id"));
                product.setId(resultSet.getInt("id"));
                resultList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public ArrayList<Product> getBy(Supplier supplier) {
        String query = "SELECT * FROM product WHERE supplierid ='" + supplier.getId() + "';";
        ArrayList<Product> resultList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                ProductCategoryDao category = ProductCategoryDaoJDBC.getInstance();
                ProductCategory category1 = category.find(Integer.parseInt(resultSet.getString("categoryId")));
                Float defaultPrice = Float.parseFloat(resultSet.getString("defaultPrice").replaceAll(resultSet.getString("defaultCurrency"), ""));
                Product result = new Product(
                        resultSet.getString("name"),
                        defaultPrice,
                        resultSet.getString("defaultCurrency"),
                        resultSet.getString("description"),
                        category1, supplier);
                result.setId(resultSet.getInt("id"));
                resultList.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public ArrayList<Product> getBy(ProductCategory category) {
        String query = "SELECT * FROM product WHERE categoryid ='" + category.getId() + "';";
        ArrayList<Product> resultList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                SupplierDao supplier = SupplierDaoJDBC.getInstance();
                Supplier supplier1 = supplier.find(Integer.parseInt(resultSet.getString("supplierId")));
                Float defaultPrice = Float.parseFloat(resultSet.getString("defaultPrice").replaceAll(resultSet.getString("defaultCurrency"), ""));
                Product result = new Product(
                        resultSet.getString("name"),
                        defaultPrice,
                        resultSet.getString("defaultCurrency"),
                        resultSet.getString("description"),
                        category, supplier1);
                result.setId(resultSet.getInt("id"));
                resultList.add(result);
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
