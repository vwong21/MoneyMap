package com.example.Transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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

import com.example.Transactions.api.entity.Transaction;
import com.example.Transactions.database.TransactionsRepo;
import com.example.Transactions.exception.TransactionNotFoundException;
import com.example.Transactions.service.TransactionsService;

@ExtendWith(MockitoExtension.class)
class TransactionsApplicationTests {
    @Mock
    private TransactionsRepo repo;

    @InjectMocks
    private TransactionsService service;

    private UUID userId;
    private UUID transactionId;
    private UUID categoryId;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
        transactionId = UUID.randomUUID();
        categoryId = UUID.randomUUID();
    }

    // Create Transaction
    @Test
    public void createTransaction_ShouldSaveAndReturnTransaction() {
        Transaction saved = new Transaction(userId, "Big Way Hot Pot", new BigDecimal("47.28"), "Hang out with friends",
                categoryId);
        when(repo.save(any(Transaction.class))).thenReturn(saved);
        Transaction result = service.createTransaction(userId, "Big Way Hot Pot", new BigDecimal("47.28"),
                "Hang out", categoryId);
        assertEquals("Big Way Hot Pot", result.getTitle());
        assertEquals(new BigDecimal("47.28"), result.getAmount());
        assertEquals("Hang out with friends", result.getDescription());
        assertEquals(categoryId, result.getCategoryId());
        verify(repo).save(any(Transaction.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void createTransaction_NullAndEmptyTitleShouldThrowIllegalArgumentException(String title) {
        assertThrows(IllegalArgumentException.class, () -> service.createTransaction(userId, title,
                new BigDecimal("47.28"), "Hang out", categoryId));
        verify(repo, times(0)).save(any(Transaction.class));
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("nonPositiveValues")
    public void createTransaction_NullEmptyNegativeZeroAmountShouldThrowIllegalArgumentException(BigDecimal amount) {
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction(userId, "Big Way Hot Pot", amount, "Hang out", categoryId));
        verify(repo, times(0)).save(any(Transaction.class));
    }

    static Stream<BigDecimal> nonPositiveValues() {
        return Stream.of(BigDecimal.ZERO, BigDecimal.valueOf(-3));
    }

    // Get Transactions
    @Test
    public void getTransaction_ShouldReturnTransaction() {
        Transaction transaction = new Transaction(userId, "Big Way Hot Pot", new BigDecimal("47.28"), "Hang out",
                categoryId);
        when(repo.findById(transactionId)).thenReturn(Optional.of(transaction));
        Transaction result = service.getTransaction(transactionId, userId);
        assertEquals(transaction, result);
        verify(repo).findById(transactionId);
    }

    @Test
    public void getTransaction_NullTransactionIdShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.getTransaction(null, userId));
        verify(repo, never()).findById(any(UUID.class));
    }

    @Test
    public void getTransaction_NonExistentTransactionIdShouldThrowTransactionNotFoundException() {
        when(repo.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(TransactionNotFoundException.class, () -> service.getTransaction(transactionId, userId));
        verify(repo).findById(transactionId);
    }

}
