package com.codecool.shop.dao.implementation.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractDaoJDBC {

    private static final String DBURL = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBURL, DB_USER, DB_PASSWORD);
    }
}
