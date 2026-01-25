package main.java.com.example.transactions.enums;

public enum Type {
    INCOME {
        @Override
        public double apply(double current, double amount) {
            return current + amount;
        }
    },

    EXPENSE {
        @Override 
        public double apply(double current, double amount) {
            return current - amount;
        }
    };

    public abstract double apply(double current, double amount);
}
