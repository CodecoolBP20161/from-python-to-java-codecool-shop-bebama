package com.codecool.shop.controller;

import com.codecool.shop.cart.ShippingOption;
import com.codecool.shop.cart.implementation.Order;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryController {

    public static ModelAndView renderDelivery(Request req, Response res) {
        Map params = new HashMap<String, Object>();
        List<ShippingOption> deliveryOptions = new ShippingServiceController().getShippingCost(Order.getOrder(req).getShippingCity());
        params.put("deliveryOptions", deliveryOptions);
        return new ModelAndView(params, "product/delivery_options");
    }
}
