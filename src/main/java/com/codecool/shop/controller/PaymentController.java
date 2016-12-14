package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import spark.*;

import java.util.*;

/**
 * Created by cickib on 2016.12.14..
 */
public class PaymentController {

    public static ModelAndView renderPayment(Request req, Response res) {
        if (req.session().attribute("isLoggedIn")) {
            Map params = new HashMap<>();
            params.put("order", Order.getOrder(req));
            return new ModelAndView(params, "product/payment");
        } else {
            Map params = new HashMap<>();
            return new ModelAndView(params, "log_in_page");
        }
    }
}
