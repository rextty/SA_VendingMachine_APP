package com.example.sa_vendingmachine_app.Model.Entity;

public class MessageEntity {

    private String productId;

    private String message;

    private int msgType;

    private boolean isNotify;

    public MessageEntity() {
        isNotify = false;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNotify(boolean isNotify) {
        this.isNotify = isNotify;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getProductId() {
        return productId;
    }

    public int getMsgType() {
        return msgType;
    }

    public String getMessage() {
        return message;
    }

    public boolean getNotify() {
        return isNotify;
    }
}
