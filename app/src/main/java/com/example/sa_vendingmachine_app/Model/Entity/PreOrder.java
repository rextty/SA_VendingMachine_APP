package com.example.sa_vendingmachine_app.Model.Entity;

import java.sql.Blob;

public class PreOrder {

    private String machineSerialNumber;

    private String userId;

    private String expireDate;

    private boolean isTake;

    private Blob qrcode;

    private int totalPrice;

    public PreOrder() {
        this.isTake = false;
        this.totalPrice = 0;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setMachineSerialNumber(String machineSerialNumber) {
        this.machineSerialNumber = machineSerialNumber;
    }

    public void setTake(boolean take) {
        isTake = take;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setQrcode(Blob qrcode) {
        this.qrcode = qrcode;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public String getMachineSerialNumber() {
        return machineSerialNumber;
    }

    public String getUserId() {
        return userId;
    }

    public boolean getTake() {
        return isTake;
    }

    public Blob getQrcode() {
        return qrcode;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
