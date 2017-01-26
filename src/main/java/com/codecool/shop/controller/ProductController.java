package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import com.codecool.shop.dao.implementation.jdbc.ProductDaoJDBC;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;


public class ProductController extends AbstractController {

    public static ModelAndView renderProducts(Request req, Response res) throws IOException, URISyntaxException {
        setLoginDetails(req);
        return setParams(Order.getOrder(req), "/", ProductDaoJDBC.getInstance().getAll());
    }
}
