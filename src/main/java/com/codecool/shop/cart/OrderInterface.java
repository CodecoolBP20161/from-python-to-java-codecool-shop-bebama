package com.codecool.shop.cart;

import com.codecool.shop.cart.implementation.Order;
import spark.Request;

import java.util.List;
import java.util.Map;

public interface OrderInterface {

    void setStatus(String status);

    String getStatus();

    void setCheckoutItems(Map<String, String> items);

    Map<String, String> getCheckoutItems();

    static Order getOrder(Request req) {
        return req.session().attribute("Cart");
    }

    List<LineItem> getListOfSelectedItems();

    void add(LineItem item);

    void edit(LineItem item);

    LineItem find(LineItem item);

    int getTotalQuantity();

    Float getTotalPrice();
}