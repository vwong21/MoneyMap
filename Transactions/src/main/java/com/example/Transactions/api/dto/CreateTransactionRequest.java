package com.example.Transactions.api.dto;

import java.util.UUID;

public class CreateTransactionRequest {
    private Float amount;
    private String description;
    private UUID categoryId;

    public CreateTransactionRequest(Float amount, String description, UUID categoryId) {
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
    }

    // Amount
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
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
