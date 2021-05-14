package model;

import java.util.List;

public class ListOfTransaction {
    public List<Transaction> transactions;
    public double spending=0,income=0;

    public ListOfTransaction(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
