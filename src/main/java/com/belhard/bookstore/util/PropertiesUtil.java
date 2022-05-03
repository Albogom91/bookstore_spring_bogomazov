package main.java.com.belhard.bookstore.util;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class PropertiesUtil {
    private static final String PATH_TO_PROPERTIES_REMOTE = "dbremote.properties";
    private static final String PATH_TO_PROPERTIES_LOCAL = "dblocal.properties";

    public static Properties getDbProperties () {
        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(PATH_TO_PROPERTIES_REMOTE);
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (properties.isEmpty()) {
            throw new RuntimeException("Properties object is empty!");
        }
        return properties;
    }
}
