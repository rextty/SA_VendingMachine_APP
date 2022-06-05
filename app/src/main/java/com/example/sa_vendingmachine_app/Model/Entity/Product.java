package com.example.sa_vendingmachine_app.Model.Entity;

import java.sql.Blob;

public class Product {

    private int id;

    private int quantity;

    private int price;

    private String name;

    private Blob image;

    public Product() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Blob getImage() {
        return image;
    }

    public int getId() {
        return id;
    }
}