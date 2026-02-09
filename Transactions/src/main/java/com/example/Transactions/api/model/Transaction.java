package com.example.Transactions.api.model;

import java.sql.Date;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private UUID userId;
    private Float amount;
    private Date createdAt;
    private String description;
    private UUID categoryId;

    public Transaction(UUID id, UUID userId, Float amount, Date createdAt, String description, UUID categoryId) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.description = description;
        this.categoryId = categoryId;
    }

    // ID
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    // UserId
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    // Amount
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    // CreatedAt
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Category Id
    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
