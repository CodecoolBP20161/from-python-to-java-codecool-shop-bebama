package com.codecool.shop.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.Map;

public class AnalitycsController {

    public static ModelAndView getStatistics(Request request, Response response) {

        String apiKey = request.queryParams("apiKey");
        String neededStatistic = request.queryParams("statistic");
        Map params = new HashMap<>();
        params.put("apikey", apiKey);
        return new ModelAndView(params, "statistics/test");
    }
}
