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
