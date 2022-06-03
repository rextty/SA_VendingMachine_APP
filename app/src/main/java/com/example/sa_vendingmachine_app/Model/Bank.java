package com.example.sa_vendingmachine_app.Model;

public class Bank {

    private int deposit = 1000;

    public Bank() {}

    public void refund(int amount) {
        this.deposit += amount;
    }

    public void debit(int amount) {
        this.deposit -= amount;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getDeposit() {
        return deposit;
    }
}
