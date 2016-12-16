package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import spark.*;

import java.util.*;

/**
 * Created by cickib on 2016.12.14..
 */
public class CheckoutController {

    public static ModelAndView renderCheckout(Request req, Response res) {
        UserController.isLoggedIn(req);
        Map params = new HashMap<>();
        if (req.session().attribute("isLoggedIn")) {
            params.put("isLoggedIn", UserController.isLoggedIn(req));
            params.put("order", Order.getOrder(req));
            return new ModelAndView(params, "product/checkout_form");
        } else {
            return new ModelAndView(params, "user/not_logged_in");
        }
    }
}
