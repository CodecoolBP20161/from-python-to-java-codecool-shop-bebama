package com.codecool.shop.cart;

public class ShippingOption {

    private String name;
    private String iconClass;
    private String destination;
    private Float cost;
    private String origin;
    private String description;
    private Integer time;
    private Integer distance;

    public ShippingOption(String name, String iconClass, String destination, Integer cost, String origin, String description, Integer time, Integer distance) {
        this.name = name;
        this.iconClass = iconClass;
        this.destination = destination.split(",")[0];
        this.cost = cost.floatValue();
        this.origin = origin.split(",")[0];
        this.description = description;
        this.time = time;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
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
