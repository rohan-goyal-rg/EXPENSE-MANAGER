package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Summary {
    public TreeMap<String,ListOfTransaction> treeMap=new TreeMap<>(new  Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMMM yyyy");
            int t=0;
            try {
                Date d1=simpleDateFormat.parse(o1);
                Date d2=simpleDateFormat.parse(o2);
                t= d1.compareTo(d2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return t;
        }
    });

    public void addTransaction(String date, double amount, int type) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("dd MMMM yyyy");
        Date dateFormat=format.parse(date);
        Transaction transaction=new Transaction(dateFormat,amount,type);

        SimpleDateFormat formatInMonth =new SimpleDateFormat("MMMM yyyy");
        Date dateInMonth=format.parse(date);

        if (treeMap.containsKey(formatInMonth.format(dateInMonth))){
            treeMap.get(formatInMonth.format(dateInMonth)).transactions.add(transaction);
        }

        else {
        treeMap.put(formatInMonth.format(dateInMonth),new ListOfTransaction(new ArrayList<>(
                Arrays.asList(transaction)
        )));

        }
        if(type==TransactionType.INCOME){

            treeMap.get(formatInMonth.format(dateInMonth)).income+=amount;
        }
        else {

            treeMap.get(formatInMonth.format(dateInMonth)).spending+=amount;
        }
    }

    public void editTransaction(String date, int transactionNo, String newDate) {
        if (treeMap.containsKey(date)) {
            try {
                Transaction transaction=treeMap.get(date).transactions.get(transactionNo - 1);
                treeMap.get(date).transactions.remove(transactionNo - 1);

                if (transaction.type==TransactionType.INCOME){
                    treeMap.get(date).income-=transaction.amount;
                }
                else {
                    treeMap.get(date).spending-=transaction.amount;
                }

                if (treeMap.get(date).transactions.size() == 0) {
                    treeMap.remove(date);

                }
                addTransaction(newDate,transaction.amount,transaction.type);
                System.out.println("Successfully edit!");

            } catch (Exception e) {
                System.out.println("Please enter valid transaction No");
            }
        }
        else {
            System.out.println("Please enter valid date");
        }
    }
    public void removeTransaction(String date,int transactionNo) {
        if (treeMap.containsKey(date)) {
            try {
                Transaction transaction = treeMap.get(date).transactions.get(transactionNo - 1);
                if (transaction.type == TransactionType.INCOME) {
                    treeMap.get(date).income -= transaction.amount;
                } else {
                    treeMap.get(date).spending -= transaction.amount;
                }
                treeMap.get(date).transactions.remove(transactionNo - 1);

                if (treeMap.get(date).transactions.size() == 0) {
                    treeMap.remove(date);
                }
                System.out.println("Remove successfully!");

            } catch (Exception e) {
                System.out.println("Please enter valid transaction No");

            }
        }
        else {
            System.out.println("Please enter valid date");
        }
    }
    @Override
    public String toString() {


        StringBuilder stringBuilder=new StringBuilder();
        for (Map.Entry<String,ListOfTransaction> summaryTransaction:treeMap.entrySet()){
            stringBuilder.append("    "+summaryTransaction.getKey()+"\n");

            ListOfTransaction listOfTransaction=summaryTransaction.getValue();
            listOfTransaction.transactions.sort(Comparator.comparing(o -> o.date));

            int count=1;
            for (Transaction transaction:listOfTransaction.transactions){
                stringBuilder.append(count++ + ")"+transaction+"\n");
            }
            stringBuilder.append("TotalIncome:-"+listOfTransaction.income)
                    .append("  TotalSpending:-"+listOfTransaction.spending+"\n");

        }
        return "Summary:-\n"
                + stringBuilder;

    }
}
