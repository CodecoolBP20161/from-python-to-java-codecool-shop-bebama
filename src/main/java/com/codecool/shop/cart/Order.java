package com.codecool.shop.cart;
import spark.Request;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by makaimark on 2016.11.15..
 */
public class Order {
    private List<LineItem> listOfSelectedItems = new ArrayList<>();
    public Order() {}
    public void add(LineItem item){
        Boolean match = false;
        for (int i = 0; i < this.listOfSelectedItems.size(); i++) {
            if (this.listOfSelectedItems.get(i).getProduct() == item.getProduct()) {
                this.listOfSelectedItems.get(i).incQuantity(item.getQuantity());
                match = true;
            }
        }
        if (!match){
            this.listOfSelectedItems.add(item);
        }
    }
    public List<LineItem> getListOfSelectedItems(){
        return this.listOfSelectedItems;
    }
    public void remove(LineItem item){
        this.listOfSelectedItems.remove(item);
    }
    public int getTotalQuantity(){
        return this.listOfSelectedItems.stream().mapToInt(o -> o.getQuantity()).sum();
    }
    public static void addItemToSession(Request req, LineItem item) {
        if ( req.session().attribute("Cart") != null ) {
            Order order = req.session().attribute("Cart");
            order.add(item);
            req.session().attribute("Cart", order);
        } else {
            Order order = new Order();
            order.add(item);
            req.session().attribute("Cart", order);
        }
    }
}