package com.example.Categories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Categories.api.entity.Category;
import com.example.Categories.database.CategoriesRepo;
import com.example.Categories.exception.ResourceNotFoundException;
import com.example.Categories.exception.UnauthorizedAccessException;
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

    @Test
    public void createCategory_EmptyTypeShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.createCategory(userId, "Bubble Tea", ""));
        verify(repo, never()).save(any());
    }

    @Test
    public void createCategory_EmptyNameShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.createCategory(userId, "", "Dessert"));
        verify(repo, never()).save(any());
    }

    @Test
    public void createCategory_NullNameShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.createCategory(userId, null, "Dessert"));
        verify(repo, never()).save(any());
    }

    // Get Categories
    @Test
    public void getCategories_ShouldReturnListOfCategories() {
        List<Category> list = List.of(new Category(userId, "Bubble Tea", "Dessert"),
                new Category(userId, "Hot Pot", "Dinner"));
        when(repo.findAll()).thenReturn(list);
        List<Category> result = service.getCategories(userId);

        assertEquals(new Category(userId, "Bubble Tea", "Dessert"), result.get(0));
        assertEquals(new Category(userId, "Hot Pot", "Dinner"), result.get(1));

        verify(repo).findAll();
    }

    // Delete Category
    @Test
    public void deleteCategory_ShouldReturnCategoryId() {
        Category category = new Category(userId, "Bubble Tea", "Dessert");
        when(repo.findById(any(UUID.class))).thenReturn(Optional.of(category));
        doNothing().when(repo).delete(category);
        UUID result = service.deleteCategory(categoryId, userId);

        assertEquals(categoryId, result);

        verify(repo).findById(any(UUID.class));
        verify(repo).delete(category);
    }

    @Test
    public void deleteCategory_NonExistentCategoryId_ShouldThrowResourceNotFoundException() {
        when(repo.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteCategory(categoryId, userId));

        verify(repo).findById(categoryId);
        verify(repo, never()).delete(any(Category.class));
    }

    @Test
    public void deleteCategory_UserIdParamDoesNotMatchCategoryUserId_ShouldThrowUnauthorizedAccessException() {
        UUID newUserId = UUID.randomUUID();
        Category category = new Category(newUserId, "Bubble Tea", "Dessert");
        when(repo.findById(any(UUID.class))).thenReturn(Optional.of(category));
        assertThrows(UnauthorizedAccessException.class, () -> service.deleteCategory(categoryId, userId));
        verify(repo).findById(categoryId);
        verify(repo, never()).delete(any(Category.class));
    }

    @Test
    public void deleteCategory_NullCategoryId_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteCategory(null, userId));
        verify(repo, never()).findById(any());
    }

    // Update Category
    @Test
    public void updateCategory_NullCategoryId_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateCategory(userId, null, "Bubble Tea", "Dessert"));
        verify(repo, never()).findById(any());
    }

    @Test
    public void updateCategory_NonExistentCategoryId_ShouldThrowResourceNotFoundException() {
        UUID newCategoryId = UUID.randomUUID();
        when(repo.findById(newCategoryId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> service.updateCategory(userId, newCategoryId, "Bubble Tea", "Dessert"));
        verify(repo).findById(newCategoryId);
    }

    @Test
    public void updateCategory_UserIdParamDoesNotMatchCategoryUserId_ShouldThrowUnauthorizedAccessException() {
        UUID randomUserId = UUID.randomUUID();
        Category foundCategory = new Category(userId, "Bubble Tea", "Dessert");
        when(repo.findById(categoryId)).thenReturn(Optional.of(foundCategory));
        assertThrows(UnauthorizedAccessException.class,
                () -> service.updateCategory(randomUserId, categoryId, "Bubble Tea", "Dessert"));
    }
}