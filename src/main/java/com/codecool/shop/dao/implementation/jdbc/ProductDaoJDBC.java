package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.*;
import com.codecool.shop.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makaimark on 2016.11.29..
 */
public class ProductDaoJDBC extends AbstractDaoJDBC implements ProductDao {
    private static ProductDaoJDBC instance = null;

    public static ProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        try (Connection connection = getConnection()) {
            PreparedStatement query = connection.prepareStatement("INSERT INTO product (p_name, p_description, defaultPrice, defaultCurrency, categoryId, supplierId) VALUES (?, ?, ?, ?, ?, ?);");
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
        String query = "SELECT * FROM product LEFT JOIN category ON product.categoryid=category.c_id LEFT JOIN supplier ON product.supplierid=supplier.s_id WHERE p_id ='" + id + "';";
        Product result = null;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query);
        ) {
            if (rs.next()) {
                ProductCategory category = new ProductCategory(rs.getString("c_name"), rs.getString("department"), rs.getString("c_description"));
                category.setId(rs.getInt("c_id"));
                Supplier supplier = new Supplier(rs.getString("s_name"), rs.getString("s_description"));
                supplier.setId(rs.getInt("s_id"));
                Float defaultPrice = Float.parseFloat(rs.getString("defaultprice").replaceAll(rs.getString("defaultcurrency"), ""));
                result = new Product(rs.getString("p_name"), defaultPrice, rs.getString("defaultcurrency"), rs.getString("p_description"), category, supplier);
                result.setId(id);
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
            statement.execute("DELETE FROM product WHERE id = '" + id + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        return getProductList("SELECT * FROM product LEFT JOIN category ON product.categoryid=category.c_id LEFT JOIN supplier ON product.supplierid=supplier.s_id ;");
    }

    @Override
    public ArrayList<Product> getBy(Supplier supplier) {
        return getProductList("SELECT * FROM product LEFT JOIN category ON product.categoryid=category.c_id LEFT JOIN supplier ON product.supplierid=supplier.s_id WHERE supplierid ='" + supplier.getId() + "';");
    }

    @Override
    public ArrayList<Product> getBy(ProductCategory category) {
        return getProductList("SELECT * FROM product LEFT JOIN category ON product.categoryid=category.c_id LEFT JOIN supplier ON product.supplierid=supplier.s_id WHERE categoryid ='" + category.getId() + "';");
    }

    public ArrayList<Product> getProductList(String query){
        ArrayList<Product> resultList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query);
        ) {
            while (rs.next()) {
                ProductCategory category = new ProductCategory(rs.getString("c_name"), rs.getString("department"), rs.getString("c_description"));
                category.setId(rs.getInt("c_id"));
                Supplier supplier = new Supplier(rs.getString("s_name"), rs.getString("s_description"));
                supplier.setId(rs.getInt("s_id"));
                Float defaultPrice = Float.parseFloat(rs.getString("defaultprice").replaceAll(rs.getString("defaultcurrency"), ""));
                Product prod = new Product(rs.getString("p_name"), defaultPrice, rs.getString("defaultcurrency"), rs.getString("p_description"), category, supplier);
                prod.setId(rs.getInt("p_id"));
                resultList.add(prod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
