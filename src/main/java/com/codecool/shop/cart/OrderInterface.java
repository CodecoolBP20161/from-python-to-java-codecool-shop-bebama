package com.codecool.shop.cart;

import com.codecool.shop.cart.implementation.Order;
import spark.Request;

import java.util.List;

public interface OrderInterface {

    static Order getOrder(Request req) {
        return req.session().attribute("Cart");
    }

    void setStatus(String status);

    String getStatus();

    void setCheckoutFields(Request req) throws NoSuchFieldException, IllegalAccessException;

    String getName();

    String getEmail();

    String getPhone();

    String getBillingCity();

    String getShippingCity();

    List<LineItem> getListOfSelectedItems();

    void add(LineItem item);

    void edit(LineItem item);

    LineItem find(LineItem item);

    int getTotalQuantity();

    Float getTotalPrice();
}
