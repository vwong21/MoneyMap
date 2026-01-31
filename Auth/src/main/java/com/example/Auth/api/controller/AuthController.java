package com.example.Auth.api.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
        try {
            String token = service.login(request.getEmail(), request.getPassword());

            return ResponseEntity.ok(
                Map.of("token", token)
            );

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }


    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> getUser(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UUID userId = (UUID) authentication.getPrincipal();
        UserResponse user = service.getUser(userId);

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/users/me")
    public ResponseEntity<UserResponse> updateUser(
            Authentication authentication,
            @RequestBody PatchRequest request
    ) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UUID userId = (UUID) authentication.getPrincipal();
        UserResponse user = service.updateUser(userId, request);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String token) {

        return ResponseEntity.noContent().build();
    }
}
