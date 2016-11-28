package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import spark.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by balazsando on 2016.11.28..
 */
abstract class AbstractController {

    static Map params = new HashMap<>();

    static void setParams(Request req) {
        params.put("categories", ProductCategoryDaoMem.getInstance().getAll());
        params.put("suppliers", SupplierDaoMem.getInstance().getAll());
        params.put("order", Order.getOrder(req));
    }
}
