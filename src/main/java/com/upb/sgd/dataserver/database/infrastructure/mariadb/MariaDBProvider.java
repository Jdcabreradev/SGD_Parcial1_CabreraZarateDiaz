package com.upb.sgd.dataserver.database.infrastructure.mariadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDBProvider {
    private static final String url = "jdbc:mariadb://localhost:3306/SGD_DEV?useSSL=false&serverTimezone=UTC";
    private static final String dbUser = "remote";
    private static final String dbPassword = "remote";

    public static Connection MariaDBConn(){
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            System.out.println("DataServer connected to database.");
            return connection;
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            return null;
        }
    }
}
