package com.example.Auth.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.stereotype.Repository;

import com.example.Auth.api.model.User;

@Repository
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
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getFirstName());
        stmt.setString(3, user.getLastName());
        stmt.setString(4, user.getPassword());

        stmt.executeUpdate();

        System.out.println("Successfully added user to database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // login method

    // getUser method

    // putUser method

    // deleteUser method
}
