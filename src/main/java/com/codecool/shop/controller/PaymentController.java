package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import org.apache.http.client.utils.URIBuilder;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;


/**
 * Created by cickib on 2016.12.14..
 */
public class PaymentController extends AbstractController {

    private static final String URL = "http://localhost:60020";
    private static Boolean validPaymentCode;

    public static ModelAndView renderPayment(Request req, Response res) {
        setLoginDetails(req);
        if (params.get("isLoggedIn").equals(true)) {
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

    public static Response paymentService(Request req, Response res) throws URISyntaxException {
        Random rand = new Random();
        int randomCode = rand.nextInt(9999) + 1000;
        req.session().attribute("randomCode", randomCode);
        URIBuilder builder = new URIBuilder(URL + "/paymentservice?randomCode=" + randomCode);
        builder.addParameter("recipient", Order.getOrder(req).getEmail());
        builder.addParameter("recipientName", Order.getOrder(req).getName());
        try {
            execute(builder.build());
            Order.getOrder(req).setPaymentCode(randomCode);
        } catch (IOException e) {
            System.out.println("An error occurred with the payment service.\n" + e.getMessage());
            e.printStackTrace();
        }
        res.redirect("/paymentservice");
        return res;
    }

    public static ModelAndView renderPaymentChecker(Request req, Response res) {
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

    public static ModelAndView checkPaymentCode(Request req, Response res) {
        Integer code = Integer.parseInt(req.queryParams("payment-code"));
        if (Order.getOrder(req).getPaymentCode().equals(code)) {
            params.put("checked", true);
            validPaymentCode = true;
            try {
                URIBuilder builder = new URIBuilder(URL + "/accepted");
                execute(builder.build());
            } catch (Exception e) {
                System.out.println("An error occurred with the payment service.\n" + e.getMessage());
                e.printStackTrace();
            }
            Order.dropOrder(req);
            return new ModelAndView(params, "product/payment_check");
        } else {
            params.put("checked", false);
            validPaymentCode = false;
            return new ModelAndView(params, "product/payment_check");
        }
    }
}