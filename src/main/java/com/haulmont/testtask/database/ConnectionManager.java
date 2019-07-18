package com.haulmont.testtask.database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private Properties properties = new Properties();

    private ConnectionManager(String configPath) throws IOException {
        properties.load(new BufferedInputStream(new FileInputStream(configPath)));
    }

    private static class ConnectionManagerHolder {
        private static ConnectionManager instance;

        static {
            try {
                instance = new ConnectionManager("config.properties");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ConnectionManager getInstance() {
        return ConnectionManagerHolder.instance;
    }

    public Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url") + properties.getProperty("db.name");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}
