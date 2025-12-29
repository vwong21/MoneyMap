package com.example.Auth.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.example.Auth.api.model.User;

@Repository
public class AuthRepository {
    
    private final DataSource datasource;

    public AuthRepository(DataSource datasource) {
        this.datasource = datasource;
    }

    public void register(User user) {
        String sql = "INSERT INTO users (email, first_name, last_name, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = datasource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
    ){
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getFirstName());
        stmt.setString(3, user.getLastName());
        stmt.setString(4, user.getPassword());

        stmt.executeUpdate();

        System.out.println("Successfully added user to database");
        } catch (Exception e) {
            throw new RuntimeException("Failed to register user", e);
        }
    }

    // login method

    // getUser method

    // putUser method

    // deleteUser method
}
