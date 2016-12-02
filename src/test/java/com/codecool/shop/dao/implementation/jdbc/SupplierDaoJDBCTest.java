package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.DefaultStock;
import com.codecool.shop.model.Supplier;
import org.junit.*;
import org.postgresql.jdbc.PgConnection;

import java.sql.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by cickib on 2016.11.30..
 */
public class SupplierDaoJDBCTest {

    private static SupplierDaoJDBC instance;
    private static Supplier supplier;

    @Before
    public void setUp() throws Exception {
//        tests use the test db
        AbstractDaoJDBC.setConnection("testConnection.properties");
        DefaultStock stock = new DefaultStock("");
        stock.populateData();

        instance = SupplierDaoJDBC.getInstance();
        supplier = new Supplier("Amazon", "Digital content and services");
        supplier.setId(1);
    }

    @Test
    public void getInstance() throws Exception {
        assertEquals(SupplierDaoJDBC.getInstance(), instance);
    }

    @Test
    public void add() throws Exception {
        int before = instance.getAll().size();
        instance.add(supplier);
        int after = instance.getAll().size();
        assert (before < after);
    }

    @Test
    public void findAmazon() throws Exception {
        assertEquals("Amazon", instance.find(1).getName());
    }

    @Test
    public void remove() throws Exception {
//        Adding an extra supplier to be removed
        instance.add(supplier);
//        The id of the last element is needed, but it's different with every run of of the test, hence a query to get the current last id
        int result = 0;
        Connection connection = instance.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT s_id FROM supplier ORDER BY s_id DESC LIMIT 1");
        if (rs.next()) {
            result = rs.getInt("s_id");
        }
        int before = instance.getAll().size();
        instance.remove(result);
        int after = instance.getAll().size();
        assert (before > after);
    }

    @Test
    public void getAll() throws Exception {
//        we know that at the moment there's only 4 suppliers in the db
        assertEquals(4, instance.getAll().size());
    }

    @Test
    public void getConnection() throws Exception {
        assertEquals(PgConnection.class, instance.getConnection().getClass());
    }

    @After
    public void tearDown() throws Exception {
        instance.getAll().stream().filter(sup -> sup.getId() >= 5).forEach(sup -> instance.remove(sup.getId()));
    }
}