package com.belhard.bookstore.dao.dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.belhard.bookstore.util.PropertiesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DbConfigurator {
    private static Logger logger = LogManager.getLogger();
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
            logger.info("Database connection was established.");
        }
        logger.debug("Database was accessed.");
        return connection;
    }
}

