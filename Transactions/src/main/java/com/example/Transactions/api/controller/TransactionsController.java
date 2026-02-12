package com.example.Transactions.api.controller;


import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Transactions.api.dto.CreateTransactionRequest;
import com.example.Transactions.api.entity.Transaction;
import com.example.Transactions.service.TransactionsService;

@RestController
public class TransactionsController {
    private final TransactionsService service;

    public TransactionsController(TransactionsService service) {
        this.service = service;
    }
    
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(@RequestBody CreateTransactionRequest request, Authentication authentication) {
        
        UUID userID = (UUID) authentication.getPrincipal();

        Transaction transaction = service.createTransaction(userID, request.getAmount(), request.getDescription(), request.getCategoryId());

        return ResponseEntity.ok(transaction);
    }
}
