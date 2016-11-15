package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.ProductCategory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        Map params = new HashMap<>();
        List<ProductCategory> allCategories = productCategoryDataStore.getAll();
        params.put("categories", allCategories);
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderProductsByCategory(Request req, Response res) {
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        String categoryId = req.params(":id");
        int id = Integer.parseInt(categoryId);
        Map params = new HashMap<>();
        ProductCategory category = productCategoryDataStore.find(id);
        params.put("categories", category);
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderProductsBySupplier(Request req, Response res) {
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        String supplierId = req.params(":id");
        int id = Integer.parseInt(supplierId);
        Map params = new HashMap<>();
        Supplier supplier = (Supplier) supplierDataStore.find(id);
        params.put("supplier", supplier);
        return new ModelAndView(params, "product/index");
    }

}
