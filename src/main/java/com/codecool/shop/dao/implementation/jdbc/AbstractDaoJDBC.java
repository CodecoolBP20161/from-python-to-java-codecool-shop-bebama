package com.codecool.shop.dao.implementation.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AbstractDaoJDBC {

    private static String DBURL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBURL, DB_USER, DB_PASSWORD);
    }

    public static void setConnection(String fileName) throws IOException {
        Properties pro = new Properties();
        FileInputStream in = new FileInputStream("./src/main/resources/" + fileName);
        pro.load(in);

        // getting values from property file
        DBURL = pro.getProperty("DBURL");
        DB_USER = pro.getProperty("DB_USER");
        DB_PASSWORD = pro.getProperty("DB_PASSWORD");
    }
}
