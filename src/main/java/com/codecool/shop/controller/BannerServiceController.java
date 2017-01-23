package com.codecool.shop.controller;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.net.URISyntaxException;

public class BannerServiceController {

    private static final String SERVICE_URL = "http://localhost:60000";

    public String getBanner() throws URISyntaxException, IOException {
        StringEntity jsonstring = new StringEntity("{}");

        return Request.Post(SERVICE_URL + "/banner").body(jsonstring)
                .execute().returnContent().asString();
    }
}
