package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.model.ProductCategory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class CategoryController extends AbstractController{

    public static ModelAndView renderProducts(Request req, Response res) {
        setParams(req);
        int categoryId = Integer.parseInt(req.params(":id"));
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        List<ProductCategory> category = new ArrayList<>();
        category.add(productCategoryDataStore.find(categoryId));
        params.put("filter", category);
        params.put("redirect", "/category/" + categoryId);
        return new ModelAndView(params, "product/index");
    }


}
