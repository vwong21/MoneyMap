package com.example.Budgets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

}
