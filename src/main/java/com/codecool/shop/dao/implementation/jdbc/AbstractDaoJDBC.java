package com.codecool.shop.dao.implementation.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AbstractDaoJDBC {

    private static final String DBURL = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBURL, DB_USER, DB_PASSWORD);
    }

    public static void main(String[] args) throws IOException {
        Properties pro = new Properties();
        FileInputStream in = new FileInputStream("/home/makaimark/Desktop/Codecool/JAVA/from-python-to-java-codecool-shop-bebama/src/main/resources/public/config/config.properties");
        pro.load(in);
// getting values from property file
        System.out.println(pro.getProperty("DBURL"));
        System.out.println(pro.getProperty("DB_USER"));
        System.out.println(pro.getProperty("DB_PASSWORD"));
    }
}
