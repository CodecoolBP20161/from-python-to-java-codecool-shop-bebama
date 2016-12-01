package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.model.*;
import org.junit.*;

import java.sql.*;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by cickib on 2016.11.30..
 */
public class ProductDaoJDBCTest {
    private ProductDaoJDBC instance;
    private ProductCategory category;
    private Supplier supplier;
    private Product productFire;

    @Before
    public void setUp() throws Exception {
        instance = ProductDaoJDBC.getInstance();
        category = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        supplier = new Supplier("Amazon", "Digital content and services");
        category.setId(1);
        supplier.setId(1);
        productFire = new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", category, supplier);
        productFire.setId(1);
    }

    @Test
    public void add() throws Exception {
        int before = instance.getAll().size();
        instance.add(productFire);
        int after = instance.getAll().size();
        assert (before < after);
    }

    @Test
    public void find() throws Exception {
        assertEquals("Random laptop", instance.find(5).getName());
    }

    @Test
    public void remove() throws Exception {
//        Adding an extra product to be removed
        instance.add(productFire);
//        The id of the last element is needed, but it's different with every run of of the test, hence a query to get the current last id
        int result = 0;
        Connection connection = instance.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT p_id FROM product ORDER BY p_id DESC LIMIT 1");
            if (rs.next()) {
                result = rs.getInt("p_id");
            }
        int before = instance.getAll().size();
        instance.remove(result);
        int after = instance.getAll().size();
        assert (before > after);
    }

//    The current db has 7 products
    @Test
    public void getAll() throws Exception {
        assertEquals(7, instance.getAll().size());
    }

//    the category in setUp() has the same amount of products as the corresponding category in the db
    @Test
    public void getByCategorySize() throws Exception {
        assertEquals(instance.getBy(category).size(), ProductCategoryDaoJDBC.getInstance().find(1).getProducts().size());
    }

//    the category in setUp() has the same product with id 2 as the category in the db
    @Test
    public void getByCategoryId() throws Exception {
        assertEquals(instance.getBy(category).get(2).toString(), ProductCategoryDaoJDBC.getInstance().find(1).getProducts().get(2).toString());
    }

//    the supplier in setUp() has the same amount of products as the corresponding supplier in the db
    @Test
    public void getBySupplierSize() throws Exception {
        assertEquals(instance.getBy(supplier).size(), SupplierDaoJDBC.getInstance().find(1).getProducts().size());
    }

//    the supplier in setUp() has the same product with id 1 as the supplier in the db
    @Test
    public void getBySupplierId() throws Exception {
        assertEquals(instance.getBy(supplier).get(1).toString(), SupplierDaoJDBC.getInstance().find(1).getProducts().get(1).toString());
    }

    @After
    public void tearDown() throws Exception {
        instance.getAll().stream().filter(prod -> prod.getId() > instance.getAll().size()).forEach(prod -> instance.remove(prod.getId()));
    }
}