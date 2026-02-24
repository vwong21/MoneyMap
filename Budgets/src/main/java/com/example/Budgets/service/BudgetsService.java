package com.example.Budgets.service;

import org.springframework.stereotype.Service;

import com.example.Budgets.database.BudgetsRepo;

@Service
public class BudgetsService {
    private final BudgetsRepo repo;

    public BudgetsService(BudgetsRepo repo) {
        this.repo = repo;
    }
}
