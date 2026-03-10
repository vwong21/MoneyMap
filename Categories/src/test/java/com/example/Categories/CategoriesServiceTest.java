package com.example.Categories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Categories.api.entity.Category;
import com.example.Categories.database.CategoriesRepo;
import com.example.Categories.service.CategoriesService;

@ExtendWith(MockitoExtension.class)
public class CategoriesServiceTest {
    @Mock
    private CategoriesRepo repo;

    @InjectMocks
    private CategoriesService service;

    private UUID userId;
    private UUID categoryId;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
        categoryId = UUID.randomUUID();
    }

    // Create Category
    @Test
    public void createCategory_ShouldSaveCategory() {
        Category saved = new Category(userId, "Bubble Tea", "Dessert");
        when(repo.save(any(Category.class))).thenReturn(saved);
        Category result = service.createCategory(userId, "Bubble Tea", "Dessert");
        assertEquals("Bubble Tea", result.getName());
        assertEquals("Dessert", result.getType());
        verify(repo).save(any(Category.class));
    }
}