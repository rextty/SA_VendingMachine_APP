package com.example.sa_vendingmachine_app.Model;

public class VendingMachineDAL {

    private String name;

    private String state;

    public VendingMachineDAL() {}

    public VendingMachineDAL(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }
}
