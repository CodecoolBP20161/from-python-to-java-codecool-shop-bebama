package com.codecool.shop.controller;

import com.codecool.shop.HashClass;
import com.codecool.shop.cart.User;
import com.codecool.shop.cart.implementation.Order;
import com.codecool.shop.dao.implementation.jdbc.*;
import com.codecool.shop.email.service.EmailSenderService;
import spark.ModelAndView;
import spark.*;

import java.io.IOException;
import java.net.URISyntaxException;
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

    private static void signUpLogic(Request req, Response res){
        User newUser = new User(req.queryParams("name"), req.queryParams("email"), req.queryParams("pwd"), false);
        params.put("existingEmail", false);
        params.remove("name");
        params.remove("email");
        try {
            users.add(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String recipient = req.queryParams("email");
        String recipientName = req.queryParams("name");
        try {
            EmailController.builder("bebamashop@gmail.com", recipient, "Welcome email", recipientName, EmailSenderService.formatWelcomeEmail(recipientName));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        res.redirect("/successful_registration");
    }

    public static ModelAndView getFormData(Request req, Response res) throws Exception {
        if (users.find(req.queryParams("name")) == null) {
            params.remove("existingName");
            if (users.findEmail(req.queryParams("email")) == null) {
                signUpLogic(req, res);
            } else {
                params.put("name", req.queryParams("name"));
                params.put("email", req.queryParams("email"));
                params.put("existingEmail", true);
                res.redirect("/signup");
            }
        } else {
            params.put("name", req.queryParams("name"));
            params.put("email", req.queryParams("email"));
            params.put("existingName", true);
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
            req.session().removeAttribute("failedLogin");
            params.remove("failedLogin");
            res.redirect("/");
        }
        else {
            params.put("failedLogin", true);
            req.session().attribute("failedLogin", true);
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