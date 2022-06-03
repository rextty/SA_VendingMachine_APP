package com.example.sa_vendingmachine_app.Model.Entity;

import java.sql.Blob;

public class PreOrder {

    private String productId;

    private String machineSerialNumber;

    private String userId;

    private String orderDate;

    private String expireDate;

    private boolean isTake;

    private Blob qrcode;

    public PreOrder() {}

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setMachineSerialNumber(String machineSerialNumber) {
        this.machineSerialNumber = machineSerialNumber;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setTake(boolean take) {
        isTake = take;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public String getMachineSerialNumber() {
        return machineSerialNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getUserId() {
        return userId;
    }

    public boolean getTake() {
        return isTake;
    }

    public void setQrcode(Blob qrcode) {
        this.qrcode = qrcode;
    }

    public Blob getQrcode() {
        return qrcode;
    }
}
