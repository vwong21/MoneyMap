package com.example.Categories.database;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Categories.api.entity.Category;

@Repository
public interface CategoriesRepo extends JpaRepository<Category, UUID>{
    
}
