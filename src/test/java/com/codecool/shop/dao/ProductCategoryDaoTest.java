package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.mem.ProductCategoryDaoMem;
import com.codecool.shop.model.ProductCategory;
import org.junit.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by cickib on 2016.11.30..
 */
public class ProductCategoryDaoTest {

    private static List<ProductCategory> DATA = new ArrayList<>();
    private static ProductCategoryDaoMem instance = null;
    private ProductCategory tablet;
    private ProductCategory laptop;

    @Before
    public void setUp() throws Exception {
        instance = ProductCategoryDaoMem.getInstance();
        tablet = new ProductCategory("Tablet", "Hardware",
                "A tablet");
        tablet.setId(1);
        laptop = new ProductCategory("Laptop", "Hardware",
                "A laptop");
        laptop.setId(2);
        DATA.add(tablet);
        DATA.add(laptop);

        instance.add(tablet);
        instance.add(laptop);
    }

    @Test
    public void add() throws Exception {
        int before = instance.getAll().size();
        ProductCategory telefon = new ProductCategory("Telefon", "Hardware",
                "A telefon");
        instance.add(telefon);
        int after = instance.getAll().size();
        assert (after > before);
    }

    @Test
    public void find() throws Exception {
        assertEquals(instance.find(2).getName(), "Laptop");
    }

    @Test
    public void remove() throws Exception {
        int before = instance.getAll().size();
        instance.remove(1);
        int after = instance.getAll().size();
        assert (after < before);
    }

    @Test
    public void getAll() throws Exception {
        assertEquals(instance.getAll(), DATA);
    }

    @After
    public void tearDown() throws Exception {
        instance.getAll().clear();
        DATA.clear();
    }
}