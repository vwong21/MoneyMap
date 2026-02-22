package com.example.Categories.service;

import org.springframework.stereotype.Service;

import com.example.Categories.api.entity.Category;
import com.example.Categories.database.CategoriesRepo;

@Service
public class CategoriesService {
    private final CategoriesRepo repo;

    public CategoriesService(CategoriesRepo categoriesRepo) {
        this.repo = categoriesRepo;
    }

    public Category createCategory(String name, String type) {
        Category category = new Category(name, type);
        return repo.save(category);
    }
}
