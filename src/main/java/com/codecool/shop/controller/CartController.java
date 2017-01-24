package com.codecool.shop.controller;

import com.codecool.shop.cart.LineItem;
import com.codecool.shop.cart.implementation.Order;
import com.codecool.shop.dao.implementation.jdbc.ProductDaoJDBC;
import com.codecool.shop.model.Product;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class CartController {

    public static Response addItemToCart(Request req, Response res) {
        Product product = ProductDaoJDBC.getInstance().find(Integer.parseInt(req.queryParams("id")));
        int quantity = Integer.parseInt(req.queryParams("quantity"));
        LineItem item = new LineItem(product, quantity);
        Order.getOrder(req).add(item);
        res.redirect(req.queryParams("redirect"));
        return res;
    }

    public static Response editCart(Request req, Response res) {
        Order order = Order.getOrder(req);
        List<LineItem> copy = new ArrayList<>(order.getListOfSelectedItems());
        for (LineItem item : copy) {
            int quantity = Integer.parseInt(req.queryParams("quantity_" + item.getProduct().getId()));
            order.edit(new LineItem(item.getProduct(), quantity));
        }
        res.redirect(req.queryParams("redirect"));
        return res;
    }

    public static Response checkOut(Request req, Response res) throws NoSuchFieldException, IllegalAccessException {
        Order.getOrder(req).setCheckoutFields(req);
        if(new ShippingServiceController().getShippingCost(Order.getOrder(req).getShippingCity()).size() == 0){
            res.redirect("/checkout");
        } else res.redirect("/delivery");
        return res;
    }
}