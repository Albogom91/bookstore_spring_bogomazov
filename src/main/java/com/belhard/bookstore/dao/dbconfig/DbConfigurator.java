package com.belhard.bookstore.dao.dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.belhard.bookstore.util.PropertiesUtil;

public class DbConfigurator {
    private static Connection connection;

    public static void initDbConnection() {
        try {
            Properties properties = PropertiesUtil.getDbProperties();
            String url = properties.get("db.url").toString();
            String user = properties.get("db.user").toString();
            String password = properties.get("db.password").toString();
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
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

