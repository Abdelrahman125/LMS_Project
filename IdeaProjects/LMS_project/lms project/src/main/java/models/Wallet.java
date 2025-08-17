package models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Wallet {
    private String ownerId;
    private double balance;

    public Wallet(String ownerId, double initialBalance) {
        this.ownerId = ownerId;
        this.balance = initialBalance;
    }


    public boolean deduct(double amount) {
        if (amount <= 0) {
            System.out.println("Amount to deduct must be positive.");
            return false;
        }

        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void add(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }


    public String getOwnerId() {
        return ownerId;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
}