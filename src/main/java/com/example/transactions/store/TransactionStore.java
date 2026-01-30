package example.transactions.store;

import java.util.List;
import java.util.ArrayList;
import example.transactions.model.Transaction;

public final class TransactionStore {

    private List<Transaction> transactions;

    public TransactionStore() {
        this.transactions = new ArrayList<Transaction>();
    }

    public boolean add(Transaction transaction) {
        if(transaction == null){
            throw new IllegalArgumentException("Transaction cannot be null!");
        } else {
            this.transactions.add(transaction);
            return true;
        }
    }

    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }

    public Transaction findById(String id) {
        for(Transaction item : transactions) {
            if(item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public boolean delete(String id) {
        Transaction selectedItem = this.findById(id);
        if(selectedItem == null){
            throw new IllegalArgumentException("Selected item cannot be null!");
        } else {
            transactions.remove(selectedItem);
            return true;
        }
        
    }
    
}
