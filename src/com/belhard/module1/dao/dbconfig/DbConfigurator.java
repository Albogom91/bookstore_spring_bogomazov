package com.belhard.module1.dao.dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import com.belhard.module1.util.PropertiesUtil;

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

