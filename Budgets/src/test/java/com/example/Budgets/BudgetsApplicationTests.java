package com.example.Budgets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import com.example.Budgets.api.entity.Budget;
import com.example.Budgets.database.BudgetsRepo;
import com.example.Budgets.service.BudgetsService;

@ExtendWith(MockitoExtension.class)
class BudgetsApplicationTests {

    @Mock
    private BudgetsRepo repo;

    @InjectMocks
    private BudgetsService service;

    private UUID userId;
    private UUID categoryId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
        categoryId = UUID.randomUUID();
        startDate = LocalDateTime.of(2026, 12, 1, 0, 0, 0);
        endDate = LocalDateTime.of(2026, 12, 31, 0, 0, 0);

    }

    // Create Budget
    @Test
    void createBudget_ShouldReturnBudgetId() {
        UUID result = service.createBudget(userId, categoryId, "Groceries", new BigDecimal("750.00"), startDate,
                endDate);
        assertThat(result).isNotNull();
        verify(repo).save(any(Budget.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void createBudget_EmptyTitleShouldThrowIllegalArgumentException(String title) {
        assertThrows(IllegalArgumentException.class,
                () -> service.createBudget(userId, categoryId, title, new BigDecimal("750.00"), startDate, endDate));
        verify(repo, never()).save(any(Budget.class));
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("invalidAmounts")
    void createBudget_InvalidAmountShouldThrowIllegalArgumentException(BigDecimal amount) {
        assertThrows(IllegalArgumentException.class,
                () -> service.createBudget(userId, categoryId, "Groceries", amount, startDate, endDate));
        verify(repo, never()).save(any(Budget.class));
    }

    static Stream<BigDecimal> invalidAmounts() {
        return Stream.of(
                BigDecimal.ZERO,
                new BigDecimal("-0.01"),
                new BigDecimal("-100.00"));
    }

    @Test
    void createBudget_NullStartDateShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createBudget(userId, categoryId, "Groceries", new BigDecimal("750.00"), null, endDate));
        verify(repo, never()).save(any(Budget.class));
    }

    @Test
    void createBudget_NullEndDateShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createBudget(userId, categoryId, "Groceries", new BigDecimal("750.00"), startDate, null));
        verify(repo, never()).save(any(Budget.class));
    }

    @Test
    void createBudget_EndDateBeforeStartDateShouldThrowIllegalArgumentException() {
        LocalDateTime beforeStart = startDate.minusDays(1);
        assertThrows(IllegalArgumentException.class,
                () -> service.createBudget(userId, categoryId, "Groceries", new BigDecimal("750.00"), startDate,
                        beforeStart));
        verify(repo, never()).save(any(Budget.class));
    }

    // Get Budgets

    @Test
    void getBudgets_ShouldReturnBudgetsList() {

        List<Budget> budgets = new ArrayList<Budget>();
        budgets.add(new Budget(userId, categoryId, "Groceries", new BigDecimal("750.00"), startDate, endDate));
        budgets.add(new Budget(userId, categoryId, "Dessert", new BigDecimal("250.00"), startDate, endDate));

        when(repo.findAll()).thenReturn(budgets);

        List<Budget> result = service.getBudgets(userId);

        assertThat(result.size()).isEqualTo(2);

        verify(repo).findAll();
    }

    @Test
    void getBudgets_NoEntriesShouldReturnEmptyList() {
        when(repo.findAll()).thenReturn(List.of());
        List<Budget> result = service.getBudgets(userId);
        assertThat(result.size()).isEqualTo(0);
    }

    // Delete Budget
    @Test
    void deleteBudget_ShouldCallDelete() {
        UUID budgetId = UUID.randomUUID();
        Budget budget = new Budget(userId, categoryId, "Groceries", new BigDecimal("750.00"), startDate, endDate);

        when(repo.findById(budgetId)).thenReturn(Optional.of(budget));

        service.deleteBudget(budgetId, userId);

        verify(repo).delete(budget);
    }

    @Test
    void deleteBudget_NullBudgetIdShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteBudget(null, userId));
        verify(repo, never()).delete(any(Budget.class));
    }

    @Test
    void deleteBudget_NonExistentBudgetIdShouldThrowNoSuchElementException() {
        UUID budgetId = UUID.randomUUID();

        when(repo.findById(budgetId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.deleteBudget(budgetId, userId));
        verify(repo, never()).delete(any(Budget.class));
    }

    @Test
    void deleteBudget_UnauthorizedBudgetIdShouldThrowAccessDeniedException() {
        UUID budgetId = UUID.randomUUID();
        UUID differentUserId = UUID.randomUUID();
        Budget budget = new Budget(userId, categoryId, "Groceries", new BigDecimal("750.00"), startDate, endDate);

        when(repo.findById(budgetId)).thenReturn(Optional.of(budget));

        assertThrows(AccessDeniedException.class, () -> service.deleteBudget(budgetId, differentUserId));
        verify(repo, never()).delete(any(Budget.class));
    }
}
