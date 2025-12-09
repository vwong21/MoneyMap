package com.example.Auth.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.example.Auth.api.model.User;

public class AuthRepository {
    
    private Database db;

    public AuthRepository(Database db) {
        this.db = db;
    }

    public void register(User user) {
        String sql = "INSERT INTO users (email, first_name, last_name, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
    ){
            
        } catch (Exception e) {

        }
    }

    // login method

    // getUser method

    // putUser method

    // deleteUser method
}
