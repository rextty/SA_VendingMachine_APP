package com.example.sa_vendingmachine_app.Model.Entity;

public class SalesRecord {

    private String productId;

    private String date;

    public SalesRecord() {}

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductId() {
        return productId;
    }

    public String getDate() {
        return date;
    }
}
