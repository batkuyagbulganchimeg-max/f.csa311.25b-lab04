import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BankSimulation {
    public static void main(String[] args) throws InterruptedException {

        BankAccount account = new BankAccount();

        System.out.println("Эхлэх баланс: " + account.getBalance());

        // ── Thread Priority ──
        Thread t1 = new Thread(new Customer(account, "deposit", 500), "Хадгаламж");
        Thread t2 = new Thread(new Customer(account, "withdraw", 700), "Татлага-1");
        Thread t3 = new Thread(new Customer(account, "withdraw", 600), "Татлага-2");

        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY);
        t3.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Priority дараах баланс: " + account.getBalance());

        // ── ExecutorService ──
        BankAccount account2 = new BankAccount();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(new Customer(account2, "deposit", 500));
        executor.submit(new Customer(account2, "withdraw", 700));
        executor.submit(new Customer(account2, "withdraw", 600));

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("Final Balance: " + account2.getBalance());

        // ── Log хэвлэх ──
        List<String> log = account2.getTransactionLog();
        for (int i = 0; i < log.size(); i++) {
            System.out.println((i + 1) + ". " + log.get(i));
        }
    }
}