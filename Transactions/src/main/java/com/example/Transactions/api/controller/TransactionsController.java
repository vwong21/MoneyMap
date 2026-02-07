package com.example.Transactions.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {
    
    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction() {
        
    }
}
