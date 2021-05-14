import model.Summary;
import model.Transaction;
import model.TransactionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);

        Summary summary = new Summary();


        while (true) {
            System.out.println("0)To add a transaction\n" +
                    "1)To see summary\n" +
                    "2)Do you want to Exit\n\n\" " +
                    "Choose any option :-");
            int option = sc.nextInt();
            switch (option) {
                case 0:
                    addTransaction(sc, summary);
                    System.out.println("Successfully add!");
                    break;
                case 1:
                    while (true) {
                        System.out.println(summary);
                        System.out.println("0)GO Back\n1)Select transaction");
                        int options = sc.nextInt();
                        if (options == 0) {
                            break;
                        } else {
                            System.out.println("0)To edit a transaction\n1)To remove transaction");
                            int op = sc.nextInt();
                            switch (op) {
                                case 0:
                                    editTransaction(sc, summary);
                                    break;
                                case 1:
                                    removeTransaction(sc, summary);
                            }
                        }
                    }
                    break;
                case 2:
                    return;
            }
        }
    }

    private static void removeTransaction(Scanner sc, Summary summary) throws ParseException {
        System.out.println("Enter month in format " + "\"March 2020\"");
        String date = scanner.nextLine();
        System.out.println("Enter transaction number:-");
        int transactionNo = sc.nextInt();
        summary.removeTransaction(date, transactionNo);

    }

    private static void addTransaction(Scanner sc, Summary summary) throws ParseException {

        System.out.println("Enter date in format "+ "\"dd MMMM yyyy\""+":-");
        String date=scanner.nextLine();
        System.out.println("Enter amount:-");
        double amount = sc.nextDouble();
        System.out.println("Select type:-\n0)INCOME\n1)SPENDING");
        int type = sc.nextInt();
        summary.addTransaction(date, amount, type);
    }

    private static void editTransaction(Scanner sc, Summary summary) throws ParseException {
        System.out.println("Enter month in format " + "\"March 2020\"");
        String date = scanner.nextLine();
        System.out.println("Enter transaction number:-");
        int transactionNo = sc.nextInt();
        Transaction transaction = summary.treeMap.get(date).transactions.get(transactionNo - 1);
        String str = "0)Date is:- " + transaction.date + "\n" +
                "1)Amount is:- " + transaction.amount + "\n" +
                "2)Type is:- " + (transaction.type == 0 ? "Income" : "Spending") + "\n\n" +
                "Choose any option for edit:-";
        System.out.println(str);
        int option = sc.nextInt();
        switch (option) {
            case 0:
                System.out.println("Enter date (dd MMMM yyyy)");
                String newDate = scanner.nextLine();

                summary.editTransaction(date, transactionNo, newDate);

                break;
            case 1:

                System.out.println("Enter amount");
                double amount = sc.nextDouble();

                if (transaction.type == 0) {
                    summary.treeMap.get(date).income = summary.treeMap.get(date).income - transaction.amount + amount;
                } else {
                    summary.treeMap.get(date).spending = summary.treeMap.get(date).spending - transaction.amount + amount;
                }
                transaction.amount = amount;
                System.out.println("Successfully edit!");
                break;

            case 2:
                System.out.println("Select type:\n0)INCOME\n1)SPENDING");
                int options = sc.nextInt();
                if (options == 0) {
                    if (transaction.type == 1) {
                        summary.treeMap.get(date).spending -= transaction.amount;
                        transaction.type = 0;
                        summary.treeMap.get(date).income += transaction.amount;
                        System.out.println("Successfully edit!");
                    } else {
                        System.out.println("Already type is income");
                    }
                } else {
                    if (transaction.type == 0) {
                        summary.treeMap.get(date).income -= transaction.amount;
                        transaction.type = 1;
                        summary.treeMap.get(date).spending += transaction.amount;
                        System.out.println("Successfully edit!");
                    } else {
                        System.out.println("Already type is income");
                    }
                }
        }
    }
}

