package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection { public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
            "jdbc:mysql://127.0.0.1:3306/todo",
            "root",
            "Batman@2025sql"
    );
}
}