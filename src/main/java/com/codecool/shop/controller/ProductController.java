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
import com.codecool.shop.cart.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {

    private static Map params = new HashMap<>();

    public static ModelAndView renderProducts(Request req, Response res) {
        setParams(req);
        params.put("filter", params.get("categories"));
        params.put("redirect", "/");
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderProductsByProductCategory(Request req, Response res) {
        setParams(req);
        int categoryId = Integer.parseInt(req.params(":id"));
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        List<ProductCategory> category = new ArrayList<>();
        category.add(productCategoryDataStore.find(categoryId));
        params.put("filter", category);
        params.put("redirect", "/category/" + categoryId);
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderProductsBySupplier(Request req, Response res) {
        setParams(req);
        int supplierId = Integer.parseInt(req.params(":id"));
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        List<Supplier> supplier = new ArrayList<>();
        supplier.add(supplierDataStore.find(supplierId));
        params.put("filter", supplier);
        params.put("redirect", "/supplier/" + supplierId);
        return new ModelAndView(params, "product/index");
    }

   public static void setParams(Request req) {
       params.put("categories", ProductCategoryDaoMem.getInstance().getAll());
       params.put("suppliers", SupplierDaoMem.getInstance().getAll());
       params.put("total", Order.getOrder(req).getTotalQuantity());
   }
}
