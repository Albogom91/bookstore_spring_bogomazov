package com.belhard.module1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfigurator {
    private static Connection connection;
    private static String url = "jdbc:postgresql://localhost:5432/bookstore_bh";
    private static String user = "postgres";
    private static String password = "root";

    public static void initDbConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            initDbConnection();
        }
        return connection;
    }

}
