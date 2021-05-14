package model;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Transaction {

    public Date date;
    public double amount;
    public int type;

    public Transaction(Date date, double amount, int type) {
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

    @Override
    public String toString() {
        return  "Transaction{" +
                "Amount=" + amount+
                ",date=" + date +
                ",type="
                +(type==TransactionType.INCOME?"Income":"Spending")+
                "}";

    }

}
