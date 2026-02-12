package com.example.Transactions.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.Transactions.api.entity.Transaction;
import com.example.Transactions.database.TransactionsRepo;





@Service
public class TransactionsService {

    private final TransactionsRepo repo;

    public TransactionsService(TransactionsRepo transactionsRepo) {
        this.repo = transactionsRepo;
    }
    
    public Transaction createTransaction(UUID userId, BigDecimal amount, String description, UUID categoryId) {
        LocalDateTime createdAt = LocalDateTime.now();

        Transaction transaction =  new Transaction(
            null,
            userId,
            amount,
            createdAt,
            description,
            categoryId
        );

        return repo.save(transaction);
    }
}
