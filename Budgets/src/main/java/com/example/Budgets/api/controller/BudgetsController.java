package com.example.Budgets.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.Budgets.api.dto.CreateBudgetRequest;
import com.example.Budgets.api.entity.Budget;
import com.example.Budgets.service.BudgetsService;

@RestController
public class BudgetsController {
    private final BudgetsService service;
    
    public BudgetsController(BudgetsService service) {
        this.service = service;
    }

    @PostMapping("/budgets")
    public ResponseEntity<UUID> createBudget(@RequestBody CreateBudgetRequest budget, Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(service.createBudget(userId, budget.getCategoryId(), budget.getTitle(), budget.getAmount(), budget.getStartDate(), budget.getEndDate()));
    }

    @GetMapping("/budgets")
    public ResponseEntity<List<Budget>> getBudgets(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(service.getBudgets(userId));
    }
}
