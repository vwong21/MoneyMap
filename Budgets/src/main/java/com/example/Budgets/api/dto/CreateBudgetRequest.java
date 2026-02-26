package com.example.Budgets.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateBudgetRequest {
    private UUID categoryId;
    private String title;
    private BigDecimal amount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public CreateBudgetRequest(UUID categoryId, String title, BigDecimal amount, LocalDateTime startDate, LocalDateTime endDate) {
        this.categoryId = categoryId;
        this.title = title;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public void setEndDeate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
