package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.mem.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by cickib on 2016.11.30..
 */
public class SupplierDaoTest {

    private static List<Supplier> DATA = new ArrayList<>();
    private static SupplierDaoMem instance = null;
    private Supplier apple;
    private Supplier samsung;

    @Before
    public void setUp() throws Exception {
        instance = SupplierDaoMem.getInstance();
        apple = new Supplier("Apple", "Phones");
        apple.setId(1);
        samsung = new Supplier("Samsung", "Computers");
        samsung.setId(2);
        DATA.add(apple);
        DATA.add(samsung);
        instance.add(apple);
        instance.add(samsung);
    }

    @Test
    public void add() throws Exception {
        int before = instance.getAll().size();
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        instance.add(lenovo);
        int after = instance.getAll().size();
        assert (after > before);
    }

    @Test
    public void find() throws Exception {
        assertEquals(instance.find(1).getName(), "Apple");
    }

    @Test
    public void remove() throws Exception {
        int before = instance.getAll().size();
        instance.remove(1);
        int after = instance.getAll().size();
        assert (before > after);
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