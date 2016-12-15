package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import org.apache.http.client.utils.URIBuilder;
import spark.*;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


/**
 * Created by cickib on 2016.12.14..
 */
public class PaymentController {

    private static String recipient;

    private static final String URL = "http://localhost:60020";

    public static void setRecipient(String recipient) {
        PaymentController.recipient = recipient;
    }

    public static ModelAndView renderPayment(Request req, Response res) {
        UserController.isLoggedIn(req);
        Map params = new HashMap<>();
        if (req.session().attribute("isLoggedIn").equals(true)) {
            params.put("isLoggedIn", UserController.isLoggedIn(req));
            params.put("order", Order.getOrder(req));
            if(Order.getOrder(req).getTotalQuantity() > 0){
                return new ModelAndView(params, "product/payment");
            }
            else {
                res.redirect("/");
            }
        } else {
            return new ModelAndView(params, "user/not_logged_in");
        }
        return new ModelAndView(params, "product/index");
    }

    public static String paymentService(Request request, Response response) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(URL + "/paymentservice");
        builder.addParameter("recipient", Order.getOrder(request).getEmail());
        builder.addParameter("recipientName", Order.getOrder(request).getName());
        Random rand = new Random();

        int randomCode = rand.nextInt(9999) + 1000;
        Order.getOrder(request).setPaymentCode(randomCode);
        builder.addParameter("body" , "Type this number to the required field please: " + Integer.toString(randomCode));
        try {
            execute(builder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static void execute(URI uri) throws IOException, URISyntaxException {
        org.apache.http.client.fluent.Request.Post(uri).execute();
    }
}
