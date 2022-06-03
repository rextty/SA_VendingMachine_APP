package com.example.sa_vendingmachine_app.Model.Entity;

import java.util.HashMap;
import java.util.Map;

public class ShopCart {

    private Map<Product, Integer> cars = new HashMap<>();

    private int totalPrice = 0;

    public ShopCart() {

    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<Product, Integer> getCars() {
        return cars;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void addProduct(Product product, int quantity) {
        totalPrice += product.getPrice() * quantity;
        cars.put(product, quantity);
    }
}
