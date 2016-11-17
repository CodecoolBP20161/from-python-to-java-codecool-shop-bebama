package com.codecool.shop.cart;
import spark.Request;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by makaimark on 2016.11.15..
 */
public class Order {
    private List<LineItem> listOfSelectedItems;

    private Order(){
        this.listOfSelectedItems = new ArrayList<>();
    }

    public static Order getOrder(Request req) {
        if (req.session().attribute("Cart") != null) {
            return req.session().attribute("Cart");
        } else {
            return new Order();
        }
    }

    public List<LineItem> getListOfSelectedItems(){
        return this.listOfSelectedItems;
    }

    public Order add(LineItem item) {
        LineItem product = this.find(item);
        if (product != null) {
            product.incQuantity(item.getQuantity());
        } else {
            this.listOfSelectedItems.add(item);
        }
        return this;
    }

    public void remove(LineItem item){
        this.listOfSelectedItems.remove(item);
    }

    private LineItem find(LineItem item){
        return this.listOfSelectedItems.stream().filter(i -> i.getProduct() == item.getProduct()).findFirst().orElse(null);
    }

    public int getTotalQuantity(){
        return this.listOfSelectedItems.stream().mapToInt(o -> o.getQuantity()).sum();
    }

    public Float getTotalPrice(){
        return (float)this.listOfSelectedItems.stream().mapToDouble(o -> o.getTotalPrice()).sum();
    }
}