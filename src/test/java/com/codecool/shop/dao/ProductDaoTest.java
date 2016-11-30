package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.mem.ProductDaoMem;
import com.codecool.shop.model.*;
import org.junit.*;

import java.util.*;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by cickib on 2016.11.30..
 */
public class ProductDaoTest {
    private ProductDaoMem productDaoMem;
    private ProductCategory category;
    private Supplier supplier;
    private Product productWater;
    private Product productFire;
    private List<Product> testList;

    @Before
    public void setUp() throws Exception {
        productDaoMem = productDaoMem.getInstance();
        category = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        supplier = new Supplier("Amazon", "Digital content and services");

        productWater = new Product("Amazon Water", 39.9f, "USD", "Bla-bla-bla.", category, supplier);
        productDaoMem.add(productWater);

        productFire = new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", category, supplier);
        productFire.setId(2);

        testList = new ArrayList<>();
        testList.add(productWater);
    }

    @Test
    public void add() throws Exception {
        int before = productDaoMem.getAll().size();
        productDaoMem.add(productFire);
        int after = productDaoMem.getAll().size();
        assert (before < after);
    }

    @Test
    public void find() throws Exception {
        assertEquals(productDaoMem.find(1).getName(), productWater.getName());
    }

    @Test
    public void remove() throws Exception {
        int before = productDaoMem.getAll().size();
        productDaoMem.remove(1);
        int after = productDaoMem.getAll().size();
        assert (before > after);
    }

    @Test
    public void getAll() throws Exception {
        assert (productDaoMem.getAll().size() == testList.size());
    }

    @Test
    public void getByCategory() throws Exception {
        productDaoMem.add(productFire);
        assertEquals(productDaoMem.getBy(category), category.getProducts());
    }

    @Test
    public void getBySupplier() throws Exception {
        productDaoMem.add(productFire);
        assertEquals(productDaoMem.getBy(supplier), supplier.getProducts());
    }

    @After
    public void tearDown() throws Exception {
        productDaoMem.getAll().clear();
        testList.clear();
    }
}