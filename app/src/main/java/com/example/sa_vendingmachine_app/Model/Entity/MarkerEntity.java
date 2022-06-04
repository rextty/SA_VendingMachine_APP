package com.example.sa_vendingmachine_app.Model.Entity;

public class MarkerEntity {

    private int id;

    private double Lat;

    private double Lng;

    private String name;

    private String state;

    public MarkerEntity() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public void setLng(double lng) {
        Lng = lng;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return Lat;
    }

    public double getLng() {
        return Lng;
    }

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }
}
