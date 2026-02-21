package com.example.Categories.service;

import org.springframework.stereotype.Service;

import com.example.Categories.database.CategoriesRepo;

@Service
public class CategoriesService {
    private final CategoriesRepo repo;

    public CategoriesService(CategoriesRepo categoriesRepo) {
        this.repo = categoriesRepo;
    }

    public void createCategory(){
        
    }
}
