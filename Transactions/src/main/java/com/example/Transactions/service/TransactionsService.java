package com.example.Transactions.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.Transactions.api.model.Transaction;



@Service
public class TransactionsService {
    
    public Transaction CreateTransaction(UUID userId, Float amount, String description, UUID categoryId) {
        UUID id = UUID.randomUUID();
        Date createdAt = D

        return new Transaction(categoryId, userId, amount, null, description, categoryId);
    }
}
