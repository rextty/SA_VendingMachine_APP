package com.example.sa_vendingmachine_app.Model.Entity;

public class PreOrder {

    private String productId;

    private String machineSerialNumber;

    private String userId;

    private String orderDate;

    private String expiredDate;

    private boolean isTake;

    public PreOrder() {}

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
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

    public String getExpiredDate() {
        return expiredDate;
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
}
