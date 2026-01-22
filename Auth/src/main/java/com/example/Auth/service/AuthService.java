package com.example.Auth.service;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Auth.api.dto.UserResponse;
import com.example.Auth.api.model.User;
import com.example.Auth.database.AuthRepository;
import com.example.Auth.security.JwtUtil;

@Service
public class AuthService {
    
    private final AuthRepository repo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AuthService(AuthRepository repo, JwtUtil jwtUtil) {
        this.repo = repo;
        this.jwtUtil = jwtUtil;
    }

    // register method
    public void register(User user) {
        if (
            user.getEmail() == null || user.getEmail().isBlank() || user.getFirstName() == null || user.getFirstName().isBlank() || user.getLastName() == null || user.getLastName().isBlank() || user.getPassword() == null || user.getPassword().isBlank()) {
                throw  new IllegalArgumentException("All fields must be filled out.");
            }
        
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            repo.register(user);
            System.out.println("Service call complete");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
    }

    // login method
    public String login(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled out.");
        }
        try {
            User user = repo.login(email);
            if (user == null || !encoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

            return jwtUtil.generateToken(user.getId());


        } catch (RuntimeException e) {
            throw e;
        }
    }

    // getUser method
    public UserResponse getUser(UUID userId) {
        User user = repo.getUser(userId);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName()
        );
    }

    // patchUser method

    // deleteUser method
} 
