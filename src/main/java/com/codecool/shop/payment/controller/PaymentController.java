package com.codecool.shop.payment.controller;

import com.codecool.shop.cart.LineItem;
import com.codecool.shop.cart.implementation.Order;
import com.codecool.shop.controller.EmailController;
import spark.Request;
import spark.Response;

import java.util.List;

/**
 * Created by makaimark on 2016.12.15..
 */
public class PaymentController {

    public static Response sendPaymentCode(Request req, Response res) {
        String body = String.format("Please enter the code below to the required field on our website.\n\nIdentification code: %s", req.queryParams("randomCode"));
        try {
            EmailController.builder("bebamashop@gmail.com", req.queryParams("recipient"),
                    "Payment confirmation", req.queryParams("recipientName"), body);
        } catch (Exception e) {
            System.out.println("An error occurred while sending the payment confirmation code.\n" + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public static Response sendOrderConfirmationEmail(Request req, Response res) {
        try {
            EmailController.builder("bebamashop@gmail.com",
                    Order.getOrder(req).getEmail(),
                    "Your order", Order.getOrder(req).getName(),
                    createConfirmationEmailBody(req));
        } catch (Exception e) {
            System.out.println("An error occurred while trying to send the order confirmation email.\nDetails: " + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    private static String createConfirmationEmailBody(Request req) {
        String listOfItems = "You purchased the following products: \n\n";
        List<LineItem> items = Order.getOrder(req).getListOfSelectedItems();
        for (LineItem item : items) {
            listOfItems += item.getProduct().getName() + "\n";
            listOfItems += "Quantity: " + Integer.toString(item.getQuantity()) + "\n";
            listOfItems += "Price: " + Integer.toString(Math.round(item.getTotalPrice())) + " USD \n\n";
        }
        listOfItems += "Total price: " + Integer.toString(Math.round(Order.getOrder(req).getTotalPrice())) + " USD";
        return listOfItems;
    }
}
