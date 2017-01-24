package com.codecool.shop.cart;

public class ShippingOption {

    private String destination;
    private Integer cost;
    private String origin;
    private String description;
    private Integer time;
    private Integer distance;

    public ShippingOption(String destination, Integer cost, String origin, String description, Integer time, Integer distance) {
        this.destination = destination.split(",")[0];
        this.cost = cost;
        this.origin = origin.split(",")[0];
        this.description = description;
        this.time = time;
        this.distance = distance;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
