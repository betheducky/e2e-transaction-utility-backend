package example.transactions.enums;

public enum Type {
    INCOME {
        @Override
        public double apply(double current, double amount) {
            // double Balance variable for later implementation with current
            // return current + amount;
            return amount;
        }
    },

    EXPENSE {
        @Override 
        public double apply(double current, double amount) {
            // double Balance variable for later implementation with current
            // return current - amount;
            return amount;
        }
    };

    public abstract double apply(double current, double amount);
}
