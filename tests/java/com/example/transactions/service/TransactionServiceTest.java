package tests.java.com.example.transactions.service;

import example.transactions.store.TransactionStore;
import example.transactions.service.TransactionService;
import example.transactions.enums.Type;
import example.transactions.model.Transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    @Test 
    // Creating Transaction should properly return and store it.
    void createTransaction_storesTransaction() {
        TransactionStore store = new TransactionStore();
        TransactionService service = new TransactionService(store);

        Transaction created = service.create("Espresso", 4.50, Type.EXPENSE);

        assertEquals("Espresso", created.getDescription());
        assertEquals(4.50, created.getAmount());
        assertEquals(Type.EXPENSE, created.getType());
        assertEquals(1, store.findAll().size());
    }

    @Test
    // Creating transaction with negative amount should throw an exception.
    void createTransaction_negativeAmount_throwsException() {
        TransactionStore store = new TransactionStore();
        TransactionService service = new TransactionService(store);

        assertThrows(IllegalArgumentException.class, () -> {
            service.create("Invalid", -10.4, Type.EXPENSE);
        });
    }

    @Test
    // If no amount entered, previous amount persists.
    void updateTransaction_withoutAmount_preservesExistingAmount() {
        TransactionStore store = new TransactionStore();
        TransactionService service = new TransactionService(store);

        Transaction original = service.create("Pocket protector", 12.0, Type.EXPENSE);

        Transaction updated = service.update(original.getId(), "Pocket rocket", 0.0, Type.EXPENSE);

        assertEquals(12.00, updated.getAmount());
        assertEquals("Pocket rocket", updated.getDescription());
    }
    
}
