package com.codecool.shop.cart;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private Boolean welcomeEmail;

    public User(String name, String email, String password, Boolean welcomeEmail){
        this.name = name;
        this.email = email;
        this.password = password;
        this.welcomeEmail = welcomeEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getWelcomeEmail() {
        return welcomeEmail;
    }

    public void setWelcomeEmail(Boolean welcomeEmail) {
        this.welcomeEmail = welcomeEmail;
    }
}
