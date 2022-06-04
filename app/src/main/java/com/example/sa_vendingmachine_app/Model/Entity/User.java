package com.example.sa_vendingmachine_app.Model.Entity;

public class User {

    private int id;

    private String token;

    private int permission;

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getToken() {
        return token;
    }

    public int getPermission() {
        return permission;
    }
}
