package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cickib on 2016.11.29..
 */
public class SupplierDaoJDBC extends AbstractDaoJDBC implements SupplierDao {

    private static SupplierDaoJDBC instance = null;

    public static SupplierDaoJDBC getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        try (Connection connection = getConnection()) {
            PreparedStatement query = connection.prepareStatement("INSERT INTO supplier (s_name, s_description) VALUES (?, ?);");
            query.setString(1, supplier.getName());
            query.setString(2, supplier.getDescription());
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {
        Supplier result = null;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM supplier WHERE s_id ='" + id + "';");
        ) {
            if (rs.next()) {
                result = new Supplier(rs.getString("s_name"), rs.getString("s_description"));
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
            statement.execute("DELETE FROM supplier WHERE s_id = '" + id + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> resultList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM supplier;");
        ) {
            while (rs.next()) {
                Supplier supplier = new Supplier(rs.getString("s_name"), rs.getString("s_description"));
                supplier.setId(rs.getInt("s_id"));
                supplier.setProducts(ProductDaoJDBC.getInstance().getBy(supplier));
                resultList.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
