package com.codecool.shop.controller;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.*;

/**
 * Created by makaimark on 2016.12.13..
 */

public class EmailController {
    private static final String URL = "http://localhost:60009";

    public static void builder(String sender, String recipient, String subject, String recipientName) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(URL + "/sendemail");
        builder.addParameter("sender", sender);
        builder.addParameter("recipient", recipient);
        builder.addParameter("subject", subject);
        builder.addParameter("recipientName", recipientName);
        execute(builder.build());
    }

    private static void execute(URI uri) throws IOException, URISyntaxException {
        Request.Get(uri).execute();
    }
}
