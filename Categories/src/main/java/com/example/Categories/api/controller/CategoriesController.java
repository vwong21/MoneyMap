package com.example.Categories.api.controller;

import java.util.List;
import java.util.UUID;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Categories.api.dto.CreateCategoryRequest;
import com.example.Categories.api.entity.Category;
import com.example.Categories.service.CategoriesService;

@RestController
public class CategoriesController {
    private final CategoriesService service;

    public CategoriesController(CategoriesService categoriesService) {
        this.service = categoriesService;
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryRequest categoryRequest, Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        Category newCategory = service.createCategory(userId, categoryRequest.getName(), categoryRequest.getType());

        return ResponseEntity.ok(newCategory);
    }
    
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories(Authentication authenticaiton) {
        UUID userId = (UUID) authenticaiton.getPrincipal();
        List<Category> categories = service.getCategories(userId);
        return ResponseEntity.ok(categories);
    }
}
