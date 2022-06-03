package com.example.sa_vendingmachine_app.Service;

import com.example.sa_vendingmachine_app.Model.Entity.MessageEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReplenishmentService {

    public ReplenishmentService() {
    }

    public List<MessageEntity> getAllMessage() {
        String query = "SELECT * FROM vending_machine.message;";

        return null;
    }

    public void addMessage(MessageEntity message) {
        String query = String.format("INSERT INTO vending_machine.message (productId, message, msgType, state) " +
                "VALUES ('%s', '%s', '%d', %b);", message.getProductId(), message.getMessage(), message.getMsgType(), false);
    }

    public boolean checkExistMessageByProductIdAndMsgType(MessageEntity message) {
        String query = String.format("SELECT *  FROM vending_machine.message " +
                "WHERE productId = %s AND msgType = %d;", message.getProductId(), message.getMsgType()
        );
        return false;
    }

}
