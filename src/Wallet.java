import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;



class Wallet {
    private int walletId;
    private double balance;
    private int ownerId; // userId of the owner
    private List<String> transactionHistory;
    private static int nextWalletId = 1;

    public Wallet(int ownerId, double initialBalance) {
        this.walletId = nextWalletId++;
        this.ownerId = ownerId;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Initial balance: $" + initialBalance);
    }

    public boolean deduct(double amount, String reason) {
        if (balance >= amount) {
            balance -= amount;
            addTransaction("Deducted: $" + amount + " - " + reason);
            return true;
        }
        return false;
    }

    public void add(double amount, String reason) {
        balance += amount;
        addTransaction("Added: $" + amount + " - " + reason);
    }

    private void addTransaction(String transaction) {
        String timestamp = LocalDateTime.now().toString();
        transactionHistory.add(timestamp + " - " + transaction);
    }

    // Getters and Setters
    public int getWalletId() { return walletId; }
    public double getBalance() { return balance; }
    public int getOwnerId() { return ownerId; }
    public List<String> getTransactionHistory() { return new ArrayList<>(transactionHistory); }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", balance=$" + balance +
                ", ownerId=" + ownerId +
                '}';
    }
}