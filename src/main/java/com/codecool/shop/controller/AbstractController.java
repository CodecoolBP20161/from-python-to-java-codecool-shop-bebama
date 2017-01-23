package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import com.codecool.shop.dao.implementation.jdbc.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.jdbc.SupplierDaoJDBC;
import com.codecool.shop.model.Product;
import org.json.JSONObject;
import spark.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AbstractController {

    static Map params = new HashMap<>();

    static ModelAndView setParams(Order order, String redirect, List<Product> products) throws IOException, URISyntaxException {
        JSONObject jsonObject = new JSONObject(new BannerServiceController().getBanner());
        params.put("categories", ProductCategoryDaoJDBC.getInstance().getAll());
        params.put("suppliers", SupplierDaoJDBC.getInstance().getAll());
        params.put("order", order);
        params.put("redirect", redirect);
        params.put("products", products);
        params.put("banner", jsonObject.get("Advertisement"));
        return new ModelAndView(params, "product/index");
    }
}
