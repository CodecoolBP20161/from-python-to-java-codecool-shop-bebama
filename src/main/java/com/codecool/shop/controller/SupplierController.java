package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import com.codecool.shop.dao.implementation.SupplierDaoJDBC;
import com.codecool.shop.model.Product;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.List;

public class SupplierController extends AbstractController{

    public static ModelAndView renderProducts(Request req, Response res) {
        int supplierId = Integer.parseInt(req.params(":id"));
        List<Product> products = SupplierDaoJDBC.getInstance().find(supplierId).getProducts();
        return setParams(Order.getOrder(req), "/category/" + supplierId, products);
    }
}
