import com.codecool.shop.cart.LineItem;
import com.codecool.shop.cart.Order;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import org.json.simple.*;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // populate some data for the memory storage
        populateData();
        ProductController.setSuppliers();
        ProductController.setCategories();

        // Always start with more specific routes
        get("/hello", (req, res) -> "Hello World");

        //render the products by category
        get("/category/:id", ProductController::renderProductsByProductCategory, new ThymeleafTemplateEngine());

        //render the products by supplier
        get("/supplier/:id", ProductController::renderProductsBySupplier, new ThymeleafTemplateEngine());

        post("/additemtocart", (req, res) -> {
            Product product = ProductDaoMem.getInstance().find(Integer.parseInt(req.queryParams("id")));
            int quantity = Integer.parseInt(req.queryParams("quantity"));
            LineItem item = new LineItem(product, quantity);
            Order.addItemToSession(req, item);
            res.redirect("/");
            return "";
        });


        post("/editcart", (req, res) -> {
            Product product = ProductDaoMem.getInstance().find(Integer.parseInt(req.queryParams("id")));
            int quantity = Integer.parseInt(req.queryParams("quantity"));
            Order order = req.session().attribute("Cart");
            for (int i = 0; i < order.getListOfSelectedItems().size(); i++) {
                if (order.getListOfSelectedItems().get(i).getProduct().equals(product)) {
                    if (quantity >= 1) {
                        order.getListOfSelectedItems().get(i).setQuantity(quantity);
                    } else {
                        order.getListOfSelectedItems().remove(order.getListOfSelectedItems().get(i));
                    }
                }
            }
            req.session().attribute("Cart", order);
            res.redirect("/");
            return "";
        });

        get("/showcart", (req, res) -> {
            JSONArray cart = new JSONArray();
            try {
                Order order = req.session().attribute("Cart");
                for (int i = 0; i < order.getListOfSelectedItems().size(); i++) {
                    JSONObject obj = new JSONObject();
                    obj.put("name", order.getListOfSelectedItems().get(i).getProduct().getName());
                    obj.put("price", order.getListOfSelectedItems().get(i).getProduct().getPrice());
                    obj.put("quantity", Integer.toString(order.getListOfSelectedItems().get(i).getQuantity()));
                    obj.put("id", Integer.toString(order.getListOfSelectedItems().get(i).getProduct().getId()));
                    cart.add(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cart;
        });

        // Always add generic routes to the end
        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());

        // Add this line to your project to enable the debug screen
        enableDebugScreen();
    }

    public static void populateData() {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier apple = new Supplier("Apple", "Phones");
        supplierDataStore.add(apple);
        Supplier samsung = new Supplier("Samsung", "Computers");
        supplierDataStore.add(samsung);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(phone);
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "Laptop. Just because.");
        productCategoryDataStore.add(laptop);
        ProductCategory accessories = new ProductCategory("Accessories", "Hardware", "Cell phones have become one of the most crucial personal technology purchases. Buy cell phone, iPhone case, or Bluetooth headset.");
        productCategoryDataStore.add(accessories);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("iPhone 10", 899, "USD", "iPhone. No more description needed.", phone, apple));
        productDataStore.add(new Product("Random laptop", 139, "USD", "Lorem ipsum blablabla.", laptop, samsung));
        productDataStore.add(new Product("Exploding phone", 79, "USD", "If you like to live dangerously, buy this one.", phone, samsung));
        productDataStore.add(new Product("AmazonPhone", 59, "USD", "Amazon's very own budget phone.", phone, amazon));

    }
}
