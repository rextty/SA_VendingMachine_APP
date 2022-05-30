package com.example.sa_vendingmachine_app.Model.Entity;

public enum MessageTypeEnum {
    SENSOR_ERROR(0), OUT_OF_STOCK(1);

    private final int value;

    MessageTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
