package com.example.Categories.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.Categories.api.entity.Category;
import com.example.Categories.database.CategoriesRepo;

@Service
public class CategoriesService {
    private final CategoriesRepo repo;

    public CategoriesService(CategoriesRepo categoriesRepo) {
        this.repo = categoriesRepo;
    }

    public Category createCategory(UUID userId, String name, String type) {
        Category category = new Category(userId, name, type);
        return repo.save(category);
    }
}
