package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.DefaultStock;
import com.codecool.shop.model.ProductCategory;
import org.junit.*;
import org.postgresql.jdbc.PgConnection;

import java.sql.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by cickib on 2016.11.30..
 */
public class ProductCategoryDaoJDBCTest {

    private static ProductCategoryDaoJDBC instance;
    private static ProductCategory category;

    @Before
    public void setUp() throws Exception {
//        tests use the test db
        AbstractDaoJDBC.setConnection("testConnection.properties");
        DefaultStock stock = new DefaultStock("");
        stock.populateData();

        instance = ProductCategoryDaoJDBC.getInstance();
        category = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        category.setId(1);
    }

    @Test
    public void getInstance() throws Exception {
        assertEquals(ProductCategoryDaoJDBC.getInstance(), instance);
    }

    @Test
    public void add() throws Exception {
        int before = instance.getAll().size();
        instance.add(category);
        int after = instance.getAll().size();
        assert (before < after);
    }

    @Test
    public void findTablet() throws Exception {
        assertEquals("Tablet", instance.find(1).getName());
    }

    @Test
    public void remove() throws Exception {
//        Adding an extra category to be removed
        instance.add(category);
//        The id of the last element is needed, but it's different with every run of of the test, hence a query to get the current last id
        int result = 0;
        Connection connection = instance.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT c_id FROM category ORDER BY c_id DESC LIMIT 1");
        if (rs.next()) {
            result = rs.getInt("c_id");
        }
        int before = instance.getAll().size();
        instance.remove(result);
        int after = instance.getAll().size();
        assert (before > after);
    }

    @Test
    public void getAll() throws Exception {
//        we know that at the moment there's only 4 categories in the db
        assertEquals(4, instance.getAll().size());
    }

    @Test
    public void getConnection() throws Exception {
        assertEquals(PgConnection.class, instance.getConnection().getClass());
    }

    @After
    public void tearDown() throws Exception {
        instance.getAll().stream().filter(cat -> cat.getId() >= 5).forEach(cat -> instance.remove(cat.getId()));
    }
}