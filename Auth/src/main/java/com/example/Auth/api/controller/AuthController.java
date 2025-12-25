package com.example.Auth.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.Auth.api.model.User;
import com.example.Auth.service.AuthService;

@RestController
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }
 
    @PostMapping("/auth/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        service.register(user);
        return ResponseEntity.status(200).body(user);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<User> login(@RequestBody String email, String password) {

        return ResponseEntity.ok(new User());
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
