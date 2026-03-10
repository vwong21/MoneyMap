package com.example.Categories.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Categories.api.entity.Category;
import com.example.Categories.database.CategoriesRepo;
import com.example.Categories.exception.ResourceNotFoundException;
import com.example.Categories.exception.UnauthorizedAccessException;

import jakarta.transaction.Transactional;

@Service
public class CategoriesService {
    private final CategoriesRepo repo;

    public CategoriesService(CategoriesRepo categoriesRepo) {
        this.repo = categoriesRepo;
    }

    // Create Category
    public Category createCategory(UUID userId, String name, String type) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must have a value");
        }

        if (type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be an empty string");
        }

        Category category = new Category(userId, name, type);
        return repo.save(category);
    }

    // Get Categories
    public List<Category> getCategories(UUID userId) {
        return repo.findAll().stream().filter(transaction -> transaction.getUserId().equals(userId)).toList();
    }

    // Delete Category
    public UUID deleteCategory(UUID categoryId, UUID userId) {
        Category category = repo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        if (!category.getUserId().equals(userId)) {
            throw new UnauthorizedAccessException("Unauthorized access to resource");
        }
        repo.delete(category);
        return categoryId;
    }

    // Update Category
    @Transactional
    public Category updateCategory(UUID userId, UUID categoryId, String name, String type) {
        Category category = repo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        if (!category.getUserId().equals(userId)) {
            throw new UnauthorizedAccessException("Unauthorized access to resource");
        }

        if (name != null && !name.isBlank()) {
            category.setName(name);
        }

        if (type != null && !type.isBlank()) {
            category.setType(type);
        }

        return category;
    }
}
