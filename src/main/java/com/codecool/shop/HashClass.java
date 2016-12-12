package com.codecool.shop;

import com.codecool.shop.cart.User;
import com.codecool.shop.dao.implementation.jdbc.UserDaoJDBC;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

/**
 * Created by makaimark on 2016.12.12..
 */
public class HashClass {

    private static String passwordHashingSalt = "passwordsalt";

    public void setpasswordHashingSalt(String salt) {
        passwordHashingSalt = salt;
    }

    public String getPasswordHashingSalt() {
        return passwordHashingSalt;
    }

    public String hasher(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((password + passwordHashingSalt).getBytes("UTF-8"));
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest);
    }

//    public static void main(String[] args) throws Exception {
//        String result = hasher("string");
//        System.out.println(result);
//    }

    private boolean checkPassword( String name, String password) throws Exception {
        String saltedHash = hasher(password);
        User user = UserDaoJDBC.getInstance().find(name);
        return user.getPassword().equals(saltedHash);
    }
}
