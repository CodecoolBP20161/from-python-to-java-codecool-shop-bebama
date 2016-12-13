package com.codecool.shop.email.controller;

import com.codecool.shop.email.service.EmailSenderService;
import spark.Request;
import spark.Response;

/**
 * Created by makaimark on 2016.12.13..
 */
public class EmailSenderController {

    private final EmailSenderService service;

    public EmailSenderController(EmailSenderService service){
        this.service = service;
    }

    public String emailController(Request request, Response response) {
        String sender = request.queryParams("sender");
        String recipient = request.queryParams("recipient");
        String subject = request.queryParams("subject");
        String text = request.queryParams("text");
        String recipientName = request.queryParams("recipientName");
        String userName = request.queryParams("userName");
        service.emailSender(sender, recipient, subject, text, recipientName, userName);
        return  "ok";
    }
}
