import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private int balance = 1000;
    private List<String> transactionLog = new ArrayList<>();

    public synchronized void deposit(int amount) {
        balance += amount;
        String record = Thread.currentThread().getName()
                + " | DEPOSIT | +" + amount
                + " | Баланс: " + balance;
        transactionLog.add(record);
        System.out.println(record);
    }

    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
            String record = Thread.currentThread().getName()
                    + " | WITHDRAW | -" + amount
                    + " | Баланс: " + balance;
            transactionLog.add(record);
            System.out.println(record);
        } else {
            String record = Thread.currentThread().getName()
                    + " | ТАТГАЛЗАВ | " + amount
                    + " (Баланс: " + balance + ")";
            transactionLog.add(record);
            System.out.println(record);
        }
    }

    public int getBalance() {
        return balance;
    }

    public List<String> getTransactionLog() {
        return transactionLog;
    }
}