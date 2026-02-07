package com.example.Transactions.api.model;

import java.sql.Date;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private UUID userId;
    private Float amount;
    private Date date;
    private String description;
    private UUID categoryId;

    public Transaction() {
        this.id = UUID.randomUUID()
    }
}
