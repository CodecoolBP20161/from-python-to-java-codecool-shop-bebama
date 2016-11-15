package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {

    private static Map params = new HashMap<>();

    public static ModelAndView renderProducts(Request req, Response res) {
        params.put("filterCategories", params.get("categories"));
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderProductsByProductCategory(Request req, Response res) {
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        List<ProductCategory> category = new ArrayList<>();
        category.add(productCategoryDataStore.find(Integer.parseInt(req.params(":id"))));
        params.put("filterCategories", category);
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderProductsBySupplier(Request req, Response res) {
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        List<Supplier> supplier = new ArrayList<>();
        supplier.add(supplierDataStore.find(Integer.parseInt(req.params(":id"))));
        params.put("filterCategories", supplier);
        return new ModelAndView(params, "product/index");
    }

    public static void setCategories() {
        params.put("categories", ProductCategoryDaoMem.getInstance().getAll());
    }

    public static void setSuppliers() {
        params.put("suppliers", SupplierDaoMem.getInstance().getAll());
    }
}
