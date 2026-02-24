package com.example.Budgets.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.Budgets.service.BudgetsService;

@RestController
public class BudgetsController {
    private final BudgetsService service;
    
    public BudgetsController(BudgetsService service) {
        this.service = service;
    }
}
