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

    @Test
    void createCategory_shouldSaveAndReturnCategory() {
        // Define expected results
        Category saved = new Category(userId, "Bubble Tea", "Dessert");

        // Define fake database behaviour
        when(repo.save(any(Category.class))).thenReturn(saved);

        // Run service
        Category result = service.createCategory(userId, "Bubble Tea", "Dessert");

        // Assert results
        assertEquals("Bubble Tea", result.getName());
        assertEquals("Dessert", result.getType());

        // Verfiy if database was run
        verify(repo).save(any(Category.class));
    }

    @Test
    void getCategories_shouldReturnOnlyMatchingUser() {
        Category cat1 = new Category(userId, "Food", "Expense");
        Category cat2 = new Category(UUID.randomUUID(), "Travel", "Expense");

        when(repo.findAll()).thenReturn(List.of(cat1, cat2));

        List<Category> result = service.getCategories(userId);

        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getUserId());
    }

    @Test
    void deleteCategory_shouldDeleteWhenAuthorized() {
        Category category = new Category(userId, "Food", "Expense");

        when(repo.findById(categoryId)).thenReturn(Optional.of(category));

        UUID result = service.deleteCategory(categoryId, userId);

        assertEquals(categoryId, result);
        verify(repo).delete(category);
    }

    @Test
    void deleteCategory_shouldThrowWhenNotFound() {
        when(repo.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.deleteCategory(categoryId, userId));
    }

    @Test
    void deleteCategory_shouldThrowWhenUnauthorized() {
        Category category = new Category(UUID.randomUUID(), "Food", "Expense");

        when(repo.findById(categoryId)).thenReturn(Optional.of(category));

        assertThrows(RuntimeException.class,
                () -> service.deleteCategory(categoryId, userId));
    }

    @Test
    void updateCategory_shouldUpdateFieldsWhenValid() {
        Category category = new Category(userId, "Old", "OldType");

        when(repo.findById(categoryId)).thenReturn(Optional.of(category));

        Category result = service.updateCategory(userId, categoryId, "New", "NewType");

        assertEquals("New", result.getName());
        assertEquals("NewType", result.getType());
    }

    @Test
    void updateCategory_shouldNotUpdateBlankFields() {
        Category category = new Category(userId, "Old", "OldType");

        when(repo.findById(categoryId)).thenReturn(Optional.of(category));

        Category result = service.updateCategory(userId, categoryId, "", null);

        assertEquals("Old", result.getName());
        assertEquals("OldType", result.getType());
    }

    @Test
    void updateCategory_shouldThrowWhenUnauthorized() {
        Category category = new Category(UUID.randomUUID(), "Old", "OldType");

        when(repo.findById(categoryId)).thenReturn(Optional.of(category));

        assertThrows(RuntimeException.class,
                () -> service.updateCategory(userId, categoryId, "New", "NewType"));
    }
}