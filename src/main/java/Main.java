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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.*;

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
            req.session().attribute("Cart", Order.getOrder(req).add(item));
            res.redirect(req.queryParams("redirect"));
            return "";
        });
        
        post("/editcart", (req, res) -> {
            Order order = Order.getOrder(req);
            List<LineItem> copy = new ArrayList<LineItem>(order.getListOfSelectedItems());
            for (LineItem item : copy) {
                System.out.println("wtf");
                int quantity = Integer.parseInt(req.queryParams("quantity_" + item.getProduct().getId()));
                order.edit(new LineItem(item.getProduct(), quantity));
            }
            req.session().attribute("Cart", order);
            res.redirect(req.queryParams("redirect"));
            return "";
        });

        get("/showcart", (req, res) -> {
            JSONArray cart = new JSONArray();
            try {
                Order order = Order.getOrder(req);
                for (int i = 0; i < order.getListOfSelectedItems().size(); i++) {
                    JSONObject obj = new JSONObject();
                    obj.put("name", order.getListOfSelectedItems().get(i).getProduct().getName());
                    obj.put("price", order.getListOfSelectedItems().get(i).getProduct().getPrice());
                    obj.put("quantity", Integer.toString(order.getListOfSelectedItems().get(i).getQuantity()));
                    obj.put("totalPrice", Float.toString(order.getListOfSelectedItems().get(i).getTotalPrice()));
                    obj.put("id", Integer.toString(order.getListOfSelectedItems().get(i).getProduct().getId()));
                    cart.add(obj);
                }
                JSONObject currOrder = new JSONObject();
                currOrder.put("totalPrice", Float.toString(order.getTotalPrice()));
                cart.add(currOrder);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cart;
        });

        get("/checkout", (req, res) -> new ThymeleafTemplateEngine().render(new ModelAndView(new HashMap(){{put("","");}}, "product/form")));

        post("/checkout", (req, res) -> {
            Order order = req.session().attribute("Cart");
            Map<String, String> shippingDetails = new HashMap<String, String>();
            shippingDetails.put("name", req.queryParams("name"));
            shippingDetails.put("email", req.queryParams("email"));
            shippingDetails.put("phone", req.queryParams("phone"));
            shippingDetails.put("billingAddress", req.queryParams("billing_address"));
            shippingDetails.put("shippingAddress", req.queryParams("shipping_address"));
            order.setCheckoutItems(order, shippingDetails);
            res.redirect("/payment");
            return "";
        });

        get("/payment", (req, res) ->
            new ThymeleafTemplateEngine().render(new ModelAndView(new HashMap(){{put("","");}}, "product/payment")));

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

