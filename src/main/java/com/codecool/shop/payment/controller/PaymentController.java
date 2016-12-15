package com.codecool.shop.payment.controller;

import com.codecool.shop.controller.EmailController;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by makaimark on 2016.12.15..
 */
public class PaymentController {

    public static String payment(Request request, Response response) {
        System.out.println("in payment");
        System.out.println(request.queryParams("recipient"));
        try {
            EmailController.builder("bebamashop@gmail.com", request.queryParams("recipient"),
                    "Welcome email", request.queryParams("recipientName"), request.queryParams("body"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
