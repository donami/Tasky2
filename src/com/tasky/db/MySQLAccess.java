package com.tasky.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by markus on 2017-03-02.
 */
public class MySQLAccess {
    public Connection getConnection() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/tasky", "root", "password");
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to database");
        }
    }
}
