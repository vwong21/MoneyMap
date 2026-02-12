package com.example.Transactions.database;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Transactions.api.entity.Transaction;

@Repository
public interface TransactionsRepo extends JpaRepository<Transaction, UUID> {
}
