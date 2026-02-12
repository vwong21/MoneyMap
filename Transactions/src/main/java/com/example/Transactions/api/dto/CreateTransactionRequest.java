package com.example.Transactions.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateTransactionRequest {
    private BigDecimal amount;
    private String description;
    private UUID categoryId;

    public CreateTransactionRequest(BigDecimal amount, String description, UUID categoryId) {
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
    }

    // Amount
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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
