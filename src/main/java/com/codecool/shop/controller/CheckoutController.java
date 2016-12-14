package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import spark.*;

import java.util.*;

/**
 * Created by cickib on 2016.12.14..
 */
public class CheckoutController {

    public static ModelAndView renderCheckout(Request req, Response res) {
        Map params = new HashMap<>();
        params.put("order", Order.getOrder(req));
        return new ModelAndView(params, "product/checkout_form");
    }
}
