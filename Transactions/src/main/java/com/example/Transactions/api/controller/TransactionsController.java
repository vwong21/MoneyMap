package com.example.Transactions.api.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable UUID transactionId, Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();

        Transaction transaction = service.getTransaction(transactionId, userId);

        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactionForUser(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        List<Transaction> transactions = service.getTransactionsForUser(userId);
        return ResponseEntity.ok(transactions);   
    }
}
