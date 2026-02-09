package com.example.Transactions.database;

import java.sql.Date;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;



@Repository
public class TransactionsRepo {
    
    private final DataSource datasource;

    public TransactionsRepo(DataSource datasource) {
        this.datasource = datasource;
    }

    // Create Transaction Method
    public void createTransction(UUID id, UUID userId, Float amount, Date createdAd, String description, UUID categoryId) {
        
    }
}
