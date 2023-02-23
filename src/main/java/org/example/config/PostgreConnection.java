package org.example.config;

import org.example.utils.PropertyProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

public class PostgreConnection {
    private final Connection connection;

    public PostgreConnection() {
        try {
         String url = PropertyProvider.getProperties().getProperty("datasource.url");
         String userName = PropertyProvider.getProperties().getProperty("datasource.username");
         String password = PropertyProvider.getProperties().getProperty("datasource.password");

         connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException s) {
           throw new RuntimeException();
        }

    }

    public Connection getConnection(){
        return connection;
    }

}
