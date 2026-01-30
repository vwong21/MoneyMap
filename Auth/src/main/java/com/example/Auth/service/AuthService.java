package com.example.Auth.service;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Auth.api.dto.PatchRequest;
import com.example.Auth.api.dto.UserResponse;
import com.example.Auth.api.model.User;
import com.example.Auth.database.AuthRepository;
import com.example.Auth.exception.InvalidRequestException;
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
                throw  new InvalidRequestException("All fields must be filled out.");
            }

        user.setPassword(encoder.encode(user.getPassword()));
        repo.register(user);
        
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
    @Transactional
    public UserResponse updateUser(UUID userId, PatchRequest request) {
        if (request.getEmail() == null && request.getFirstName() == null && request.getLastName() == null) {
        throw new IllegalArgumentException("At least one field must be provided");
        }
        try {
            if (request.getEmail() != null) {
            repo.updateEmail(userId, request.getEmail());
            }
            if (request.getFirstName() != null) {
                repo.updateFirstName(userId, request.getFirstName());
            }
            if (request.getLastName() != null) {
                repo.updateLastName(userId, request.getLastName());
            }
        } catch (Exception e) {
            throw e;
        }
        

        return getUser(userId);
    }

    // deleteUser method
} 
