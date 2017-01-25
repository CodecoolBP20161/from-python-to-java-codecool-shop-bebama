package com.codecool.shop.controller;

import com.codecool.shop.cart.LineItem;
import com.codecool.shop.cart.implementation.Order;
import com.codecool.shop.email.service.EmailSenderService;
import org.apache.http.client.utils.URIBuilder;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;


/**
 * Created by cickib on 2016.12.14..
 */
public class PaymentController extends AbstractController {

    private static final String URL = "http://localhost:60020";
    private static boolean validPaymentCode;

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
        URIBuilder builder = new URIBuilder(URL + "/paymentservice");
        builder.addParameter("recipient", Order.getOrder(req).getEmail());
        builder.addParameter("recipientName", Order.getOrder(req).getName());
        builder.addParameter("body", createPaymentEmailBody(req));
        try {
            execute(builder.build());
        } catch (IOException e) {
            System.out.println("An error occurred with the payment service.\n" + e.getMessage());
            e.printStackTrace();
        }
        res.redirect("/paymentservice");
        return res;
    }

    private static String createPaymentEmailBody(Request req) {
        Random rand = new Random();
        int randomCode = rand.nextInt(9999) + 1000;
        Order.getOrder(req).setPaymentCode(randomCode);
        return String.format("Please enter this identification to the required field on our website.\nIdentification code: %s", Integer.toString(randomCode));
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
        String code = req.queryParams("payment-code");
        if (Order.getOrder(req).getPaymentCode() == Integer.parseInt(code)) {
            params.put("checked", true);
            validPaymentCode = true;
            sendOrderConfirmationEmail(req);
            params.put("checked", false);
            Order.dropOrder(req);
            return new ModelAndView(params, "product/payment_check");
        } else {
            params.put("checked", false);
            validPaymentCode = false;
            return new ModelAndView(params, "product/payment_check");
        }
    }

    private static void sendOrderConfirmationEmail(Request req) {

        try {
            EmailController.builder("bebamashop@gmail.com",
                    Order.getOrder(req).getEmail(),
                    "Your order", Order.getOrder(req).getName(),
                    createConfirmationEmailBody(req));
        } catch (Exception e) {
            System.out.println("An error occurred while trying to send the order confirmation email.\nDetails: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String createConfirmationEmailBody(Request req) {
        String listOfItems = "You payed for the following products: \n\n";
        List<LineItem> items = Order.getOrder(req).getListOfSelectedItems();
        for (LineItem item : items) {
            listOfItems += item.getProduct().getName() + "\n";
            listOfItems += "Quantity: " + Integer.toString(item.getQuantity()) + "\n";
            listOfItems += "Price: " + Integer.toString(Math.round(item.getTotalPrice())) + " USD \n\n";
        }
        listOfItems += "Total price: " + Integer.toString(Math.round(Order.getOrder(req).getTotalPrice())) + " USD";
        return listOfItems;
    }

    static void sendSignUpEmail(Request req) {
        String recipient = req.queryParams("email");
        String recipientName = req.queryParams("name");
        try {
            EmailController.builder("bebamashop@gmail.com", recipient, "Welcome email", recipientName, EmailSenderService.formatWelcomeEmail(recipientName));
        } catch (Exception e) {
            System.out.println("An error occurred while trying to send the welcome email.\nDetails: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
