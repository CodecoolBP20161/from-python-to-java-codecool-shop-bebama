import com.codecool.shop.*;
import com.codecool.shop.controller.*;
import com.codecool.shop.dao.implementation.jdbc.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.io.IOException;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {

    public static void main(String[] args) throws IOException {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // populate some data for the chosen storage
        //PropertiesConfig.config();
        AbstractDaoJDBC.setConnection("connection.properties");
        DefaultStock stock = new DefaultStock("");
        stock.populateData();

        // define routes
        get("/hello", (req, res) -> "Hello World");
        get("/category/:id", CategoryController::renderProducts, new ThymeleafTemplateEngine());
        get("/supplier/:id", SupplierController::renderProducts, new ThymeleafTemplateEngine());
        post("/additemtocart", CartController::addItemToCart);
        post("/editcart", CartController::editCart);
        get("/checkout", CheckoutController::renderCheckout, new ThymeleafTemplateEngine());
        post("/checkout", CartController::checkOut);
        get("/payment", PaymentController::renderPayment, new ThymeleafTemplateEngine());
        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());
        get("/signup", UserController::renderForm, new ThymeleafTemplateEngine());
        post("/signup", UserController::getFormData);
        get("/successful_registration", UserController::success, new ThymeleafTemplateEngine());
        // Add this line to your project to enable the debug screen
        enableDebugScreen();
    }

}