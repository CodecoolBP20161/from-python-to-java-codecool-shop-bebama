package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import spark.*;

import java.util.*;

/**
 * Created by cickib on 2016.12.14..
 */
public class PaymentController {

    public static ModelAndView renderPayment(Request req, Response res) {
        UserController.isLoggedIn(req);
        Map params = new HashMap<>();
        if (req.session().attribute("isLoggedIn")) {
            params.put("isLoggedIn", UserController.isLoggedIn(req));
            params.put("order", Order.getOrder(req));
            return new ModelAndView(params, "product/payment");
        } else {
            return new ModelAndView(params, "log_in_page");
        }
    }
}
