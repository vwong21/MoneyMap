package com.example.Auth.api.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Auth.api.dto.LoginRequest;
import com.example.Auth.api.dto.PatchRequest;
import com.example.Auth.api.dto.UserResponse;
import com.example.Auth.api.model.User;
import com.example.Auth.service.AuthService;

@RestController
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }
 
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        service.register(user);
        return ResponseEntity.ok(user);

    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = service.login(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(
            Map.of("token", token)
        );
    }


    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> getUser(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        UserResponse user = service.getUser(userId);

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/users/me")
    public ResponseEntity<UserResponse> updateUser(
            Authentication authentication,
            @RequestBody PatchRequest request
    ) {
        UUID userId = (UUID) authentication.getPrincipal();
        UserResponse user = service.updateUser(userId, request);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<UUID> deleteUser(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        UUID res = service.deleteUser(userId);

        return ResponseEntity.ok(res);
    }
}
