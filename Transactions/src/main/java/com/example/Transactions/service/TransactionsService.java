package com.example.Transactions.service;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.example.Transactions.api.entity.Transaction;
import com.example.Transactions.database.TransactionsRepo;
import com.example.Transactions.exception.TransactionNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class TransactionsService {

    private final TransactionsRepo repo;

    public TransactionsService(TransactionsRepo transactionsRepo) {
        this.repo = transactionsRepo;
    }

    public Transaction createTransaction(UUID userId, String title, BigDecimal amount, String description,
            UUID categoryId) {

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        Transaction transaction = new Transaction(
                userId,
                title,
                amount,
                description,
                categoryId);

        return repo.save(transaction);
    }

    public Transaction getTransaction(UUID transactionId, UUID userId) {
        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction Id cannot be null");
        }

        Transaction transaction = repo.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        if (!transaction.getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized access to transaction");
        }

        return transaction;
    }

    public List<Transaction> getTransactionsForUser(UUID userId) {
        return repo.findAll().stream()
                .filter(transaction -> transaction.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public UUID deleteTransaction(UUID transactionId, UUID userId) {
        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction Id cannot be null");
        }
        Transaction transaction = repo.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        if (!transaction.getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized access to transaction");
        }

        repo.delete(transaction);
        return transactionId;
    }

    @Transactional
    public Transaction udpateTransaction(UUID transactionId, UUID userId, String title, BigDecimal amount,
            String description, UUID categoryId) {
        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction Id cannot be null");
        }
        Transaction transaction = repo.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        if (!transaction.getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized access to transaction");
        }

        if (title != null) {
            transaction.setTitle(title);
        }
        if (amount != null) {
            transaction.setAmount(amount);
        }
        if (description != null) {
            transaction.setDescription(description);
        }
        if (categoryId != null) {
            transaction.setCategoryId(categoryId);
        }

        return transaction;
    }
}
