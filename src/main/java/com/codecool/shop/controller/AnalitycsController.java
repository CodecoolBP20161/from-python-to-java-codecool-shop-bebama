package com.codecool.shop.controller;

import org.apache.http.client.utils.URIBuilder;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class AnalitycsController {

    private static final String URL = "http://localhost:60015";

    public static ModelAndView getStatistics(Request request, Response response) throws URISyntaxException, IOException {

        String apiKey = request.queryParams("apiKey");
        String neededStatistic = request.queryParams("statistic");
        String result;
        if (neededStatistic.equals("visitor_count")) {
            result = getVisitorCount(apiKey);
        } else if (neededStatistic.equals("visit_time_count")) {
            result = getTimeCount(apiKey);
        } else if (neededStatistic.equals("location_visits")) {
            result = getLocationVisitors(apiKey);
        } else {
            result = "invalid request";
        }
        Map params = new HashMap<>();
        params.put("apikey", apiKey);
        params.put("result", result);
        return new ModelAndView(params, "statistics/test");
    }

    private static String getVisitorCount(String apiKey) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(URL + "/api/visitor_count");
        builder.addParameter("apiKey", apiKey);
        return execute(builder.build());
    }

    private static String getTimeCount(String apiKey) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(URL + "/api/visit_time_count");
        builder.addParameter("apiKey", apiKey);
        return execute(builder.build());
    }

    private static String getLocationVisitors(String apiKey) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(URL + "/api/location_visits");
        builder.addParameter("apiKey", apiKey);
        return execute(builder.build());
    }

    private static String execute(URI uri) throws IOException, URISyntaxException {
        System.out.println(uri);
        return String.valueOf(org.apache.http.client.fluent.Request.Post(uri).execute());
    }
}
