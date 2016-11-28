package com.codecool.shop.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;


public class ProductController extends AbstractController{

    public static ModelAndView renderProducts(Request req, Response res) {
        setParams(req);
        params.put("filter", params.get("categories"));
        params.put("redirect", "/");
        return new ModelAndView(params, "product/index");
    }
}
