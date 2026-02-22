package com.example.Categories.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<Category> getCategories(UUID userId) {
        return repo.findAll().stream().filter(transaction -> transaction.getUserId().equals(userId)).collect(Collectors.toList());
    }
}
