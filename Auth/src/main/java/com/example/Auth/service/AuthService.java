package com.example.Auth.service;

import org.springframework.stereotype.Service;

import com.example.Auth.api.model.User;
import com.example.Auth.database.AuthRepository;

@Service
public class AuthService {
    
    private final AuthRepository repo;

    public AuthService(AuthRepository repo) {
        this.repo = repo;
    }

    // register method
    public void register(User user) {
        if (
            user.getEmail() == null || user.getEmail().isBlank() || user.getFirstName() == null || user.getFirstName().isBlank() || user.getLastName() == null || user.getLastName().isBlank() || user.getPassword() == null || user.getPassword().isBlank()) {
                throw  new IllegalArgumentException("All fields must be filled out.");
            }
        
        try {
            repo.register(user);
            System.out.println("Service call complete");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    // login method

    // getUser method

    // putUser method

    // deleteUser method
} 
