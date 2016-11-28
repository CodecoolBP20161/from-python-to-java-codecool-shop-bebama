package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.util.List;

/**
 * Created by makaimark on 2016.11.28..
 */
public class ProductCategoryDaoJDBC implements ProductCategoryDao {
    private static final String DBURL = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "makaimark";
    private static final String DB_PASSWORD = "920410";

    @Override
    public void add(ProductCategory category) {

    }

    @Override
    public ProductCategory find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        return null;
    }
}
