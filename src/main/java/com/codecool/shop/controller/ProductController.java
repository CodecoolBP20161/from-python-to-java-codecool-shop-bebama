package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import com.codecool.shop.dao.implementation.ProductDaoJDBC;
import spark.ModelAndView;
import spark.Request;
import spark.Response;


public class ProductController extends AbstractController{

    public static ModelAndView renderProducts(Request req, Response res) {
        return setParams(Order.getOrder(req), "/", ProductDaoJDBC.getInstance().getAll());
    }
}
