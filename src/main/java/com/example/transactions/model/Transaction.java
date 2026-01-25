package main.java.com.example.transactions.model;
import main.java.com.example.transactions.enums.Type;
import java.time.Instant;

public class Transaction {
    String id;
    String description;
    double amount;
    Type type;
    Instant createdAt;
    Instant updatedAt;

    public Transaction(String id, String description, double amount, Type type, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
