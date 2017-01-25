package com.codecool.shop.controller;

import com.codecool.shop.HashClass;
import com.codecool.shop.cart.User;
import com.codecool.shop.cart.implementation.Order;
import com.codecool.shop.dao.implementation.jdbc.UserDaoJDBC;
import com.codecool.shop.email.service.EmailSenderService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class UserController extends AbstractController {
    private static UserDaoJDBC users = UserDaoJDBC.getInstance();

    private static void switchLoginData(Request req, boolean bool) {
        req.session().attribute("isLoggedIn", bool);
        params.put("isLoggedIn", bool);
    }

    public static boolean isLoggedIn(Request req) {
        if (req.session().attribute("isLoggedIn") == null) {
            switchLoginData(req, false);
        }
        return req.session().attribute("isLoggedIn");
    }

    public static ModelAndView renderForm(Request req, Response res) {
        params.put("order", Order.getOrder(req));
        return new ModelAndView(params, "user/signup_form");
    }

    public static ModelAndView getFormData(Request req, Response res) throws Exception {
        if (users.find(req.queryParams("name")) == null) {
            params.remove("existingName");
            if (users.findEmail(req.queryParams("email")) == null) {
                params.put("existingEmail", false);
                params.remove("name");
                params.remove("email");
                signUpUser(req, res);
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

    private static void signUpUser(Request req, Response res) {
        User newUser = new User(req.queryParams("name"), req.queryParams("email"), req.queryParams("pwd"), false);
        try {
            users.add(newUser);
        } catch (Exception e) {
            System.out.println("Failed to add new user.\n" + e.getMessage());
            e.printStackTrace();
        }
        PaymentController.sendSignUpEmail(req);
        res.redirect("/successful_registration");
    }

    public static ModelAndView successfulRegistration(Request req, Response res) {
        return new ModelAndView(params, "user/successful_registration");
    }

    public static Response login(Request req, Response res) throws Exception {
        boolean pwdMatch = HashClass.checkPassword(req.queryParams("login-name"), req.queryParams("login-pwd"));
        if (pwdMatch) {
            switchLoginData(req, true);
            req.session().removeAttribute("failedLogin");
            params.remove("failedLogin");
            res.redirect("/");
        } else {
            params.put("failedLogin", true);
            req.session().attribute("failedLogin", true);
        }
        res.redirect("/");
        return res;
    }

    public static Response logout(Request req, Response res) throws Exception {
        switchLoginData(req, false);
        Order.dropOrder(req);
        res.redirect("/");
        return res;
    }
}