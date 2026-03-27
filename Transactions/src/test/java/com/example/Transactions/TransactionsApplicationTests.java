package com.example.Transactions;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.example.Transactions.api.entity.Transaction;
import com.example.Transactions.database.TransactionsRepo;
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

    @Test
    public void createTransaction_ShouldSaveAndReturnTransaction() {
        Transaction saved = new Transaction(userId, "Big Way Hot Pot", new BigDecimal("47.28"), "Hang out with friends",
                categoryId);
        when(repo.save(any(Transaction.class))).thenReturn(saved);
        Transaction result = service.createTransaction(userId, "Big Way Hot Pot", new BigDecimal("47.28"),
                "Hang out with friends", categoryId);
        assertEquals("Big Way Hot Pot", result.getTitle());
        assertEquals(new BigDecimal("47.28"), result.getAmount());
        assertEquals("Hang out with friends", result.getDescription());
        assertEquals(categoryId, result.getCategoryId());
        verify(repo).save(any(Transaction.class));
    }

}
