package com.codecool.shop.controller;

import org.apache.http.client.utils.URIBuilder;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class AnalitycsController {

    private static final String URL = "localhost:60000";

    public static ModelAndView getStatistics(Request request, Response response) throws URISyntaxException {

        String apiKey = request.queryParams("apiKey");
        String neededStatistic = request.queryParams("statistic");
        System.out.println(neededStatistic);
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

    private static String getVisitorCount(String apiKey) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(URL + "/api/visitor_count");
        builder.addParameter("apiKey", apiKey);
        return String.valueOf(builder.build());
    }

    private static String getTimeCount(String apiKey) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(URL + "/api/visit_time_count");
        builder.addParameter("apiKey", apiKey);
        return String.valueOf(builder.build());
    }

    private static String getLocationVisitors(String apiKey) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(URL + "/api/location_visits");
        builder.addParameter("apiKey", apiKey);
        return String.valueOf(builder.build());
    }
}
