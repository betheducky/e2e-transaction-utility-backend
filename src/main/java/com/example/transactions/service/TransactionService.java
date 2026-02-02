package example.transactions.service;

import example.transactions.store.TransactionStore;
import example.transactions.enums.Type;
import example.transactions.model.Transaction;
import java.util.UUID;
import java.time.Instant;
import java.util.List;

public class TransactionService {

    private final TransactionStore store;

    public TransactionService(TransactionStore store) {
        this.store = store;
    }

    public Transaction create(String description, double amount, Type transactionType) {
        if(description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank!");
        }

        if(amount == 0) {
            throw new IllegalArgumentException("Amount must be greater than zero!");
        }

        Transaction newTransaction = new Transaction(
            UUID.randomUUID().toString(),
            description,
            amount,
            transactionType,
            Instant.now(),
            Instant.now()
        );

        store.add(newTransaction);
        return newTransaction;

    }

    public List<Transaction> get() {
        return store.findAll();
    }

    public Transaction getById(String id) {
        Transaction item = store.findById(id);

        if(item == null) {
            throw new IllegalArgumentException("Item not found!");
        }

        return item;
    }

    public Transaction update(String id, String updatedDescription, double updatedAmount, Type updatedType) {

        Transaction existing = store.findById(id);

        if(existing == null){
            throw new IllegalArgumentException("Transaction not found!");
        };
        
        if (updatedDescription != null && !updatedDescription.isBlank()) {
            existing.setDescription(updatedDescription);
        }

        Type effectiveType = updatedType != null
            ? updatedType
            : existing.getType();

        if(effectiveType == null) throw new IllegalStateException("Transaction type must be defined!");

        if (updatedAmount < 0) {
            // Math logic handled using absolute value of integers
            throw new IllegalArgumentException("Amount must be non-negative!");
        }
            // Later refactor with double Balance
        double newBalance = effectiveType.apply(existing.getAmount(), updatedAmount);
            // Any non-positive & non-negative value replaced with existing amount
        existing.setAmount(updatedAmount > 0 ? newBalance : existing.getAmount());
        existing.setUpdatedAt(Instant.now());

        return existing;
    }

    public void delete(String id) {
        boolean removed = store.delete(id);

        if(!removed) {
            throw new IllegalArgumentException("Transaction not found!");
        }
    }



    
}
