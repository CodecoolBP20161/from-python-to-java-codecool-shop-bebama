package com.codecool.shop.controller;

import com.codecool.shop.cart.LineItem;
import com.codecool.shop.cart.Order;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartController {

    public static Response addItemToCart(Request req, Response res) {
        Product product = ProductDaoMem.getInstance().find(Integer.parseInt(req.queryParams("id")));
        int quantity = Integer.parseInt(req.queryParams("quantity"));
        LineItem item = new LineItem(product, quantity);
        Order.getOrder(req).add(item);
        res.redirect(req.queryParams("redirect"));
        return res;
    }

    public static Response editCart(Request req, Response res) {
        Order order = Order.getOrder(req);
        List<LineItem> copy = new ArrayList<LineItem>(order.getListOfSelectedItems());
        for (LineItem item : copy) {
            int quantity = Integer.parseInt(req.queryParams("quantity_" + item.getProduct().getId()));
            order.edit(new LineItem(item.getProduct(), quantity));
        }
        res.redirect(req.queryParams("redirect"));
        return res;
    }

    public static Response checkOut(Request req, Response res) {
        Order order = Order.getOrder(req);
        Map<String, String> shippingDetails = new HashMap<String, String>();
        shippingDetails.put("name", req.queryParams("name"));
        shippingDetails.put("email", req.queryParams("email"));
        shippingDetails.put("phone", req.queryParams("phone"));
        shippingDetails.put("billingAddress", req.queryParams("billing_address"));
        shippingDetails.put("shippingAddress", req.queryParams("shipping_address"));
        order.setCheckoutItems(order, shippingDetails);
        res.redirect("/payment");
        return res;
    }
}
