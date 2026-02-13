package com.example.Transactions.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateTransactionRequest {
    private String title;
    private BigDecimal amount;
    private String description;
    private UUID categoryId;

    public CreateTransactionRequest(String title, BigDecimal amount, String description, UUID categoryId) {
        this.title = title;
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
    }

    // Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
