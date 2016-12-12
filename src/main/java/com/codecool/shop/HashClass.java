package com.codecool.shop;

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

    private static String hasher(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((password + passwordHashingSalt).getBytes("UTF-8"));
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest);
    }

    public static void main(String[] args) throws Exception {
        String result = hasher("string");
        System.out.println(result);
    }

    private boolean checkPassword(String password) throws Exception {
        String saltedHash = hasher(password);
//        if (saltedHash.equals(this.saltedPasswordHash)) {
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }
}
