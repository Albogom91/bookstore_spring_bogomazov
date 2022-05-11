package com.belhard.bookstore.util;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class PropertiesUtil {
    private static final String PATH_TO_PROPERTIES_REMOTE = "src/main/resources/dbremote.properties";
    private static final String PATH_TO_PROPERTIES_LOCAL = "src/main/resources/dblocal.properties";
    private static final String PATH_TO_PROPERTIES_REMOTE_TOMCAT = System.getenv("TOMCAT_HOME") + "/webapps/bookstore-bogomazov/WEB-INF/classes/dbremote.properties";
    private static final String PATH_TO_PROPERTIES_LOCAL_TOMCAT = System.getenv("TOMCAT_HOME") + "/webapps/bookstore-bogomazov/WEB-INF/classes/dblocal.properties";

    private static String option;

    public static void setPathOption(String cLineArg) {
        if (cLineArg.equals("REMOTE")) {
            option = PATH_TO_PROPERTIES_REMOTE;
        } else {
            option = PATH_TO_PROPERTIES_LOCAL;
        }
    }

    public static void setPathOption() {
        option = PATH_TO_PROPERTIES_LOCAL;
    }

    public static Properties getDbProperties() {
        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(PATH_TO_PROPERTIES_REMOTE_TOMCAT);
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
