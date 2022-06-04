package com.example.sa_vendingmachine_app.Model;

public class Bank {

    private int balance = 1000;

    public Bank() {}

    public void refund(int amount) {
        this.balance += amount;
    }

    public void debit(int amount) {
        this.balance -= amount;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }
}
