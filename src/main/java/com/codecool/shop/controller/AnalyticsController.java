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

public class AnalyticsController {

    private static final String URL = "http://localhost:60015";

    public static ModelAndView getStatistics(Request request, Response response) throws URISyntaxException, IOException {

        String apiKey = request.queryParams("apiKey");
        String neededStatistic = request.queryParams("statistic");
        String result;
        String graphURL = "";
        if (neededStatistic.equals("visitor_count")) {
            result = getVisitorCount(apiKey);
            graphURL = getGraph(result);
        } else if (neededStatistic.equals("visit_time_count")) {
            result = getTimeCount(apiKey);
        } else if (neededStatistic.equals("location_visits")) {
            result = getLocationVisitors(apiKey);
        } else {
            result = "invalid request";
        }
        System.out.println(graphURL);
        Map params = new HashMap<>();
        params.put("locations", graphURL);
        return new ModelAndView(params, "statistics/analytics");
    }

    private static String getGraph(String result) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(URL + "/graph");
        builder.addParameter("result", result);
        return execute(builder.build());
    }

    private static String getVisitorCount(String apiKey) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(URL + "/api/visitors");
        builder.addParameter("apiKey", apiKey);
        return execute(builder.build());
    }

    private static String getTimeCount(String apiKey) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(URL + "/api/times");
        builder.addParameter("apiKey", apiKey);
        return execute(builder.build());
    }

    private static String getLocationVisitors(String apiKey) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(URL + "/api/locations");
        builder.addParameter("apiKey", apiKey);
        return execute(builder.build());
    }

    private static String execute(URI uri) throws IOException, URISyntaxException {
        return org.apache.http.client.fluent.Request.Get(uri).execute().returnContent().asString();
    }
}
