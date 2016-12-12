package com.codecool.shop.dao;

import com.codecool.shop.cart.User;

import java.util.List;

public interface UserDao {

    void add(User user);
    User find(int id);
    void remove(int id);

    List<User> getAll();
}
