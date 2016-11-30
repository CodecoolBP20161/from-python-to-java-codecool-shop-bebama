package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makaimark on 2016.11.28..
 */
public class ProductCategoryDaoJDBC extends AbstractDaoJDBC implements ProductCategoryDao {

    private static ProductCategoryDaoJDBC instance = null;

    public static ProductCategoryDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        try (Connection connection = getConnection()) {
            PreparedStatement query = connection.prepareStatement("INSERT INTO category (c_name, department, c_description) VALUES (?, ?, ?);");
            query.setString(1, category.getName());
            query.setString(2, category.getDepartment());
            query.setString(3, category.getDescription());
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(int id) {
        ProductCategory result = null;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM category WHERE c_id ='" + id + "';");
        ) {
            if (rs.next()) {
                result = new ProductCategory(rs.getString("c_name"), rs.getString("department"), rs.getString("c_description"));
                result.setId(id);
                result.setProducts(ProductDaoJDBC.getInstance().getBy(result));
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
            statement.execute("DELETE FROM category WHERE c_id = '" + id + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> resultList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM category;");
        ) {
            while (rs.next()) {
                ProductCategory category = new ProductCategory(rs.getString("c_name"), rs.getString("department"), rs.getString("c_description"));
                category.setId(rs.getInt("c_id"));
                category.setProducts(ProductDaoJDBC.getInstance().getBy(category));
                resultList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
