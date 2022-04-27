package com.belhard.module1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.*;

public class DbConfigurator {
    private static Connection connection;
    private static String url;
    private static String user;
    private static String password;
    private static final String PATH_TO_PROPERTIES = "dblocal.properties";

    public static void initDbConnection() {
        try {
            getProperties();
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void getProperties() {
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(PATH_TO_PROPERTIES);
            properties.load(fis);
            url = properties.get("db.url").toString();
            user = properties.get("db.user").toString();
            password = properties.get("db.password").toString();
        } catch (IOException e) {
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
