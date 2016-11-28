package com.codecool.shop.controller;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class SupplierController extends AbstractController{

    public static ModelAndView renderProducts(Request req, Response res) {
        setParams(req);
        int supplierId = Integer.parseInt(req.params(":id"));
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        List<Supplier> supplier = new ArrayList<>();
        supplier.add(supplierDataStore.find(supplierId));
        params.put("filter", supplier);
        params.put("redirect", "/supplier/" + supplierId);
        return new ModelAndView(params, "product/index");
    }
}
