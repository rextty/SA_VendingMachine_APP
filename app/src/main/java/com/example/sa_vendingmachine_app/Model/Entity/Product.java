package com.example.sa_vendingmachine_app.Model.Entity;

public class Product {

    private int id;

    private String productId;

    private int quantity;

    private int price;

    private String name;

    private String image;

    public Product() {}

    public void setProductId(String productId) {
        this.productId = productId;
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

    public void setImage(String image) {
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

    public String getImage() {
        return image;
    }

    public String getProductId() {
        return productId;
    }
}