package com.codecool.shop.cart.implementation;

import com.codecool.shop.cart.LineItem;
import com.codecool.shop.cart.OrderInterface;
import spark.Request;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makaimark on 2016.11.15..
 */
public class Order implements OrderInterface{

    private static String[] fields = new String[]{"name", "email", "phone", "billingAddress", "shippingAddress"};
    private String status;
    private String name;
    private String email;
    private String phone;
    private String billingAddress;
    private String shippingAddress;
    private List<LineItem> listOfSelectedItems;

    private Order(){
        this.listOfSelectedItems = new ArrayList<>();
    }

    public static Order getOrder(Request req) {
        if (req.session().attribute("Cart") == null) {
            req.session().attribute("Cart", new Order());
        }
        return req.session().attribute("Cart");
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    public void setCheckoutFields(Request req) throws NoSuchFieldException, IllegalAccessException {
        for (String field : fields){
            Field f = this.getClass().getDeclaredField(field);
            f.set(this, req.queryParams(field));
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public List<LineItem> getListOfSelectedItems(){
        return this.listOfSelectedItems;
    }

    public void add(LineItem item) {
        LineItem product = this.find(item);
        if (product != null) {
            product.incQuantity(item.getQuantity());
        } else {
            this.listOfSelectedItems.add(item);
        }
    }

    public void edit(LineItem item) {
        LineItem product = this.find(item);
        if (item.getQuantity() != 0){
            product.setQuantity(item.getQuantity());
        } else {
            this.listOfSelectedItems.remove(product);
        }
    }
    
    public LineItem find(LineItem item){
        return this.listOfSelectedItems.stream().filter(i -> i.getProduct() == item.getProduct()).findFirst().orElse(null);
    }

    public int getTotalQuantity(){
        return this.listOfSelectedItems.stream().mapToInt(o -> o.getQuantity()).sum();
    }

    public Float getTotalPrice(){
        return (float)this.listOfSelectedItems.stream().mapToDouble(o -> o.getTotalPrice()).sum();
    }
}