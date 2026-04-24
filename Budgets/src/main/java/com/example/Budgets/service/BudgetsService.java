package com.example.Budgets.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.example.Budgets.api.entity.Budget;
import com.example.Budgets.database.BudgetsRepo;

@Service
public class BudgetsService {
    private final BudgetsRepo repo;

    public BudgetsService(BudgetsRepo repo) {
        this.repo = repo;
    }

    public UUID createBudget(UUID userId, UUID categoryId, String title, BigDecimal amount, LocalDateTime startDate,
            LocalDateTime endDate) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title must contain a value");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date must not be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date must not be null");
        }
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        Budget budget = new Budget(userId, categoryId, title, amount, startDate, endDate);
        repo.save(budget);
        return budget.getId();
    }

    public List<Budget> getBudgets(UUID userId) {
        return repo.findAll().stream()
                .filter(budget -> budget.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public void deleteBudget(UUID budgetId, UUID userId) {
        if (budgetId == null) {
            throw new IllegalArgumentException("Budget ID must not be null");
        }
        Budget budget = repo.findById(budgetId).orElseThrow(() -> new NoSuchElementException("Budget not found"));
        if (!budget.getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized access to budget");
        }
        repo.delete(budget);
    }
}
