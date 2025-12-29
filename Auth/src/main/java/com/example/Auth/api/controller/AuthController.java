package com.example.Auth.api.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.Auth.api.dto.LoginRequest;
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
         try {
            service.register(user);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            if (service.login(request.getEmail(), request.getPassword())) {
                return ResponseEntity.ok(Map.of("message", "Login Successful"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/users/me")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(new User());
    }

    @PutMapping("/users/me")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(new User());
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String token) {

        return ResponseEntity.noContent().build();
    }
}
