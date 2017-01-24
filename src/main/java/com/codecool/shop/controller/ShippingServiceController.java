package com.codecool.shop.controller;

import com.codecool.shop.cart.ShippingOption;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ShippingServiceController {

    public static final String SERVICE_URL = "http://localhost:65011";
    public static final String ORIGIN = "Budapest";

    public List<ShippingOption> getShippingCost(String destination) {
        List<ShippingOption> options = new ArrayList<>();
        try {
            URI uri = new URIBuilder(SERVICE_URL + "/shipping-cost")
                    .addParameter("origin", ORIGIN)
                    .addParameter("destination", destination).build();
            JSONObject shippingDetails = new JSONObject(Request.Get(uri).execute().returnContent().asString());
            options.add(getDetails(shippingDetails.getJSONObject("expressCourier")));
            options.add(getDetails(shippingDetails.getJSONObject("truck")));
            options.add(getDetails(shippingDetails.getJSONObject("truckViaHighway")));
            options.add(getDetails(shippingDetails.getJSONObject("timeMachine")));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return options;
    }

    public ShippingOption getDetails(JSONObject details){
        return new ShippingOption(
                details.getString("destinationFound"),
                details.getInt("cost"),
                details.getString("originFound"),
                details.getString("details"),
                details.getInt("timeInHours"),
                details.getInt("distanceInKm"));
    }
}
