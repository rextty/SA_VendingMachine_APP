package com.example.sa_vendingmachine_app.Model.Entity;

import java.util.HashMap;
import java.util.Map;

public class ShopCart {

    private int orderId;

    private int productId;

    private int quantity;

    public ShopCart() {}

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getOrderId() {
        return orderId;
    }
}
