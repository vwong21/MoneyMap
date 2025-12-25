package com.example.Auth.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class Database {

    private final String url;
    private final String user;
    private final String password;

    public Database() {
        url = "jdbc:postgresql://127.0.0.1:5432/moneymap";
        user = "postgres";
        password = "7363";
    }

    public Connection getConnection() throws SQLException {
        final Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        return DriverManager.getConnection(url, props);
    }
    
}
