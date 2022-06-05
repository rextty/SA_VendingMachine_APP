package com.example.sa_vendingmachine_app.Model.Entity;

public class SalesRecord {

    private int productId;

    private int machineSerialNumber;

    private String date;

    private int userId;

    public SalesRecord() {}

    public void setMachineSerialNumber(int machineSerialNumber) {
        this.machineSerialNumber = machineSerialNumber;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public int getMachineSerialNumber() {
        return machineSerialNumber;
    }

    public int getProductId() {
        return productId;
    }

    public String getDate() {
        return date;
    }
}
