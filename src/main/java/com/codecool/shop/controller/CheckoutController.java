package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Created by cickib on 2016.12.14..
 */
public class CheckoutController extends AbstractController {

    public static ModelAndView renderCheckout(Request req, Response res) {
        setLoginDetails(req);
        if (params.get("isLoggedIn").equals(true)) {
            params.put("order", Order.getOrder(req));
            return new ModelAndView(params, "product/checkout_form");
        } else {
            return new ModelAndView(params, "user/not_logged_in");
        }
    }
}
