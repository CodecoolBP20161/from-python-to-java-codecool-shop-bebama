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

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();


        Map params = new HashMap<>();
        List<ProductCategory> allCategories = productCategoryDataStore.getAll();
        params.put("categories", allCategories);
        List<com.codecool.shop.model.Supplier> allSuppliers = supplierDataStore.getAll();
        params.put("suppliers", allSuppliers);
        params.put("filterCategories", allCategories);
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderProductsByProductCategory(Request req, Response res) {
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();


        Map params = new HashMap<>();
        List<ProductCategory> category = new ArrayList<ProductCategory>();
        List<ProductCategory> allCategories = productCategoryDataStore.getAll();
        params.put("categories", allCategories);
        List<com.codecool.shop.model.Supplier> allSuppliers = supplierDataStore.getAll();
        params.put("suppliers", allSuppliers);
        category.add(productCategoryDataStore.find(Integer.parseInt(req.params(":id"))));
        params.put("filterCategories", category);
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderProductsBySupplier(Request req, Response res) {
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();


        Map params = new HashMap<>();
        List<Supplier> supplier = new ArrayList<Supplier>();
        List<ProductCategory> allCategories = productCategoryDataStore.getAll();
        params.put("categories", allCategories);
        List<com.codecool.shop.model.Supplier> allSuppliers = supplierDataStore.getAll();
        params.put("suppliers", allSuppliers);
        supplier.add(supplierDataStore.find(Integer.parseInt(req.params(":id"))));
        params.put("filterCategories", supplier);
        return new ModelAndView(params, "product/index");
    }

}
