package com.example.Categories.api.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
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
        Category newCategory = service.createCategory(categoryRequest.getName(), categoryRequest.getType());

        return ResponseEntity.ok(newCategory);
    }
    
}
