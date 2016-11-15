package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        Map params = new HashMap<>();
        List<ProductCategory> allCategories = productCategoryDataStore.getAll();
        params.put("categories", allCategories);
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderProductsByProductCategory(Request req, Response res) {
        SupplierDao productCategoryDataStore = SupplierDaoMem.getInstance();

        Map params = new HashMap<>();
        List<ProductCategory> category = new ArrayList<ProductCategory>();
        category.add(productCategoryDataStore.find(Integer.parseInt(req.params(":id"))));
        params.put("categories", category);
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderProductsBySupplier(Request req, Response res) {
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        Map params = new HashMap<>();
        List<Supplier> supplier = new ArrayList<Supplier>();
        supplier.add(supplierDataStore.find(Integer.parseInt(req.params(":id"))));
        params.put("categories", supplier);
        return new ModelAndView(params, "product/index");
    }

}
