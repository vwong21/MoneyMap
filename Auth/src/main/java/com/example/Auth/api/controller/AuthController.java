package com.example.Auth.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Auth.api.model.User;

@RestController
public class AuthController {
    @PostMapping("/auth/register")
    public ResponseEntity<User> register(@RequestParam String email, String firstName, String lastName, String password) {

        return ResponseEntity.ok(new User());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<User> login(@RequestParam String email, String password) {

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
