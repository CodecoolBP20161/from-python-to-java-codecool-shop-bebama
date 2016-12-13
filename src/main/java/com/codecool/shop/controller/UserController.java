package com.codecool.shop.controller;

import com.codecool.shop.cart.User;
import com.codecool.shop.dao.implementation.jdbc.UserDaoJDBC;
import spark.ModelAndView;
import spark.*;

import java.util.*;


/**
 * Created by cickib on 2016.12.13..
 */

public class UserController extends AbstractController {
    private static Map params = new HashMap<>();
    private static UserDaoJDBC users = UserDaoJDBC.getInstance();

    public static ModelAndView renderForm(Request req, Response res) {
        return new ModelAndView(params, "signup_form");
    }

    public static ModelAndView getFormData(Request req, Response res) throws Exception {
        if (users.findEmail(req.queryParams("email")) == null) {
            User newUser = new User(req.queryParams("name"), req.queryParams("email"), req.queryParams("pwd"), false);
            params.put("existingEmail", false);
            params.remove("name");
            params.remove("email");
            users.add(newUser);
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
        return new ModelAndView(params, "successful_registration");
    }
}