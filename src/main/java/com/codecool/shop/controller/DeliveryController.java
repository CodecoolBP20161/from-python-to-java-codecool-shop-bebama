package com.codecool.shop.controller;

import com.codecool.shop.cart.ShippingOption;
import com.codecool.shop.cart.implementation.Order;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.List;

public class DeliveryController extends AbstractController {

    public static ModelAndView renderDelivery(Request req, Response res) {
        setLoginDetails(req);
        if (params.get("isLoggedIn").equals(true)) {
            if (Order.getOrder(req).getTotalQuantity() > 0) {
                List<ShippingOption> deliveryOptions = new ShippingServiceController().getShippingCost(Order.getOrder(req).getShippingCity());
                req.session().attribute("Shipping", deliveryOptions);
                params.put("deliveryOptions", deliveryOptions);
                return new ModelAndView(params, "product/delivery_options");
            } else {
                res.redirect("/");
            }
        } else {
            return new ModelAndView(params, "user/not_logged_in");
        }
        return new ModelAndView(params, "product/index");
    }
}
