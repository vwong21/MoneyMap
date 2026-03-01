package com.example.Budgets.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Budgets.api.entity.Budget;
import com.example.Budgets.database.BudgetsRepo;

@Service
public class BudgetsService {
    private final BudgetsRepo repo;

    public BudgetsService(BudgetsRepo repo) {
        this.repo = repo;
    }

    public UUID createBudget(UUID userId, UUID categoryId,  String title, BigDecimal amount, LocalDateTime startDate, LocalDateTime endDate) {
        Budget budget = new Budget(userId, categoryId, title, amount, startDate, endDate);
        repo.save(budget);
        return budget.getId();
    }

    public List<Budget> getBudgets (UUID userId) {
        return repo.findAll().stream()
            .filter(budget -> budget.getUserId().equals(userId))
            .collect(Collectors.toList());
    }

    public void delteBudget(UUID budgetId, UUID userId) {
        Budget budget = repo.findById(budgetId).orElseThrow(() -> new RuntimeException("Budget not found"));
        if (!budget.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to budget");
        }
        repo.delete(budget);
    }
}
