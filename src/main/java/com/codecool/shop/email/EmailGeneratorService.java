package com.codecool.shop.email;

import com.codecool.shop.Email;
import com.codecool.shop.PropertiesConfig;
import com.codecool.shop.dao.implementation.jdbc.AbstractDaoJDBC;
import com.codecool.shop.email.controller.EmailSenderController;
import com.codecool.shop.email.service.EmailSenderService;

import java.io.IOException;

import static spark.Spark.*;

/**
 * Created by makaimark on 2016.12.13..
 */
public class EmailGeneratorService {

    private EmailSenderController controller;

    public static void main(String[] args) throws IOException {
//        setting up the db connection
        PropertiesConfig.config();
        AbstractDaoJDBC.setConnection("connection.properties");

        port(Email.getPort());

        EmailGeneratorService service = new EmailGeneratorService();
        service.controller = new EmailSenderController(EmailSenderService.getInstance());

        get("/sendemail", service.controller::emailController);
    }

}
