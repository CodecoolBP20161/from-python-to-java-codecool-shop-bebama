package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import org.apache.http.client.utils.URIBuilder;
import spark.*;

import java.io.IOException;
import java.net.*;
import java.util.*;


/**
 * Created by cickib on 2016.12.14..
 */
public class PaymentController {
    private static Map params = new HashMap<>();

    private static boolean validPaymentCode;

    private static String recipient;

    private static final String URL = "http://localhost:60020";

    public static void setRecipient(String recipient) {
        PaymentController.recipient = recipient;
    }

    public static ModelAndView renderPayment(Request req, Response res) {
        UserController.isLoggedIn(req);
        if (req.session().attribute("isLoggedIn").equals(true)) {
            params.put("isLoggedIn", UserController.isLoggedIn(req));
            params.put("order", Order.getOrder(req));
            if (Order.getOrder(req).getTotalQuantity() > 0) {
                return new ModelAndView(params, "product/payment");
            } else {
                res.redirect("/");
            }
        } else {
            return new ModelAndView(params, "user/not_logged_in");
        }
        return new ModelAndView(params, "product/index");
    }

    public static Response paymentService(Request request, Response response) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(URL + "/paymentservice");
        builder.addParameter("recipient", Order.getOrder(request).getEmail());
        builder.addParameter("recipientName", Order.getOrder(request).getName());
        Random rand = new Random();
        int randomCode = rand.nextInt(9999) + 1000;
        Order.getOrder(request).setPaymentCode(randomCode);
        builder.addParameter("body", "Type this number to the required field please: " + Integer.toString(randomCode));
        try {
            execute(builder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.redirect("/paymentservice");
        return response;
    }


    public static ModelAndView renderChecker(Request req, Response res) {
        if (params.get("checked") == null) {
            params.put("checked", null);
        } else {
            params.put("checked", validPaymentCode);
        }
        return new ModelAndView(params, "product/payment_check");
    }


    private static void execute(URI uri) throws IOException, URISyntaxException {
        org.apache.http.client.fluent.Request.Post(uri).execute();
    }


    public static ModelAndView checkPaymentCode(Request request, Response response) {
        String code = request.queryParams("payment-code");
        if (Order.getOrder(request).getPaymentCode() == Integer.parseInt(code)) {
            params.put("checked", true);
            validPaymentCode = true;
            return new ModelAndView(params, "product/payment_check");
        } else {
            params.put("checked", false);
            validPaymentCode = false;
            return new ModelAndView(params, "product/payment_check");
        }
    }
}
