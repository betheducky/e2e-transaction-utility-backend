package main.java.com.example.transactions.store;

import java.util.List;
import java.util.ArrayList;
import main.java.com.example.transactions.model.Transaction;

public class TransactionStore {

    private List<Transaction> transactions;

    public TransactionStore() {
        this.transactions = new ArrayList<Transaction>();
    }

    public void add(Transaction transaction) {
        this.transactions.add(transaction);
    }
    public void findAll() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public Transaction findById(String id) {
        for(Transaction item : transactions) {
            if(item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public void delete(String id) {
        Transaction selectedItem = this.findById(id);
        transactions.remove(selectedItem);
    }
    
}
