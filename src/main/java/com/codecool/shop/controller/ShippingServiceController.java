package com.codecool.shop.controller;

import com.codecool.shop.cart.ShippingOption;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

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
            JSONObject shippingDetails = new JSONObject(org.apache.http.client.fluent.Request.Get(uri).execute().returnContent().asString());
            options.add(getDetails(shippingDetails.getJSONObject("expressCourier"), "Express Courier", "fa fa-globe"));
            options.add(getDetails(shippingDetails.getJSONObject("truck"), "Truck", "fa fa-truck"));
            options.add(getDetails(shippingDetails.getJSONObject("truckViaHighway"), "Truck via highway", "fa fa-road"));
            options.add(getDetails(shippingDetails.getJSONObject("timeMachine"), "Time Machine", "fa fa-magic"));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return options;
    }

    public ShippingOption getDetails(JSONObject details, String name, String iconClass){
        return new ShippingOption(
                name, iconClass,
                details.getString("destinationFound"),
                details.getInt("cost"),
                details.getString("originFound"),
                details.getString("details"),
                details.getInt("timeInHours"),
                details.getInt("distanceInKm"));
    }

    public String checkCity(Request req, Response res){
        List<ShippingOption> shippingOptions = getShippingCost(req.queryParams("city"));
        if(shippingOptions.size() == 0) {
            return "invalid";
        } else if (shippingOptions.get(0).getDistance() > 300) {
            return "too_far";
        } else return "valid";
    }
}
