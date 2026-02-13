package com.example.Transactions.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Transactions.api.entity.Transaction;
import com.example.Transactions.database.TransactionsRepo;

@Service
public class TransactionsService {

    private final TransactionsRepo repo;

    public TransactionsService(TransactionsRepo transactionsRepo) {
        this.repo = transactionsRepo;
    }
    
    public Transaction createTransaction(UUID userId, String title, BigDecimal amount, String description, UUID categoryId) {
        LocalDateTime createdAt = LocalDateTime.now();

        Transaction transaction =  new Transaction(
            userId,
            title,
            amount,
            createdAt,
            description,
            categoryId
        );

        return repo.save(transaction);
    }

    public Transaction getTransaction(UUID transactionId, UUID userId) {
        Transaction transaction = repo.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to transaction");
        }

        return transaction;
    }

    public List<Transaction> getTransactionsForUser(UUID userId) {
        return repo.findAll().stream()
            .filter(transaction -> transaction.getUserId().equals(userId))
            .collect(Collectors.toList());
    }
}
