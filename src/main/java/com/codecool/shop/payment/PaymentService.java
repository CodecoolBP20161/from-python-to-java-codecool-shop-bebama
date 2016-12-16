package com.codecool.shop.payment;

import com.codecool.shop.payment.controller.PaymentController;

import static spark.Spark.*;
import static spark.Spark.port;

/**
 * Created by makaimark on 2016.12.15..
 */
public class PaymentService {


    public static void main(String[] args) {

        port(60020);

        post("/paymentservice", PaymentController::payment);
    }
}
