package com.codecool.shop.controller;

import com.codecool.shop.HashClass;
import com.codecool.shop.cart.User;
import com.codecool.shop.cart.implementation.Order;
import com.codecool.shop.dao.implementation.jdbc.*;
import spark.ModelAndView;
import spark.*;

import java.util.*;


/**
 * Created by cickib on 2016.12.13..
 */

public class UserController extends AbstractController {
    private static Map params = new HashMap<>();
    private static UserDaoJDBC users = UserDaoJDBC.getInstance();

    public static boolean isLoggedIn(Request request){
        if(request.session().attribute("isLoggedIn") == null){
            request.session().attribute("isLoggedIn", false);
            params.put("isLoggedIn", false);
            params.put("failedLogin", false);
        }
        return request.session().attribute("isLoggedIn");
    }

    public static ModelAndView renderForm(Request req, Response res) {
        params.put("categories", ProductCategoryDaoJDBC.getInstance().getAll());
        params.put("suppliers", SupplierDaoJDBC.getInstance().getAll());
        params.put("order", Order.getOrder(req));
        params.put("products", ProductDaoJDBC.getInstance().getAll());
        return new ModelAndView(params, "user/signup_form");
    }

    public static ModelAndView getFormData(Request req, Response res) throws Exception {
        if (users.findEmail(req.queryParams("email")) == null) {
            User newUser = new User(req.queryParams("name"), req.queryParams("email"), req.queryParams("pwd"), false);
            params.put("existingEmail", false);
            params.remove("name");
            params.remove("email");
            users.add(newUser);
            String recipient = req.queryParams("email");
            String recipientName = req.queryParams("name");
            EmailController.builder("bebamashop@gmail.com", recipient, "Welcome email", recipientName);
            res.redirect("/successful_registration");
        } else {
            params.put("name", req.queryParams("name"));
            params.put("email", req.queryParams("email"));
            params.put("existingEmail", true);
            res.redirect("/signup");
        }
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView success(Request req, Response res) {
        return new ModelAndView(params, "user/successful_registration");
    }

    public static ModelAndView login(Request req, Response res) throws Exception {
        boolean pwdMatch = HashClass.checkPassword(req.queryParams("login-name"), req.queryParams("login-pwd"));
        if(pwdMatch) {
            req.session().attribute("isLoggedIn", true);
            params.put("isLoggedIn", isLoggedIn(req));
            params.put("failedLogin", false);
            res.redirect("/");
        }
        else {
            params.put("failedLogin", true);
        }
        res.redirect("/");
        return ProductController.renderProducts(req, res);
    }

    public static ModelAndView logout(Request req, Response res) throws Exception {
        req.session().attribute("isLoggedIn", false);
        params.put("isLoggedIn", false);
        Order.dropOrder(req);
        return ProductController.renderProducts(req, res);
    }
}