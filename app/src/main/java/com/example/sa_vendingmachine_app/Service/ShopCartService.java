package com.example.sa_vendingmachine_app.Service;

import com.example.sa_vendingmachine_app.Model.DBMgr;
import com.example.sa_vendingmachine_app.Model.Entity.PreOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShopCartService {

    private DBMgr dbMgr = new DBMgr();

    public ShopCartService() {}

    public void savePreOrder(PreOrder preOrder) {
        dbMgr.savePreOrderInformation(preOrder);
    }

    public ArrayList<PreOrder> getAllPreOrder() {
        ArrayList<PreOrder> preOrders = new ArrayList<>();

        try {
            ResultSet resultSet = dbMgr.getAllPreOrder();

            while (resultSet.next()) {
                PreOrder preOrder = new PreOrder();

                preOrder.setExpireDate(resultSet.getString("expireDate"));
                preOrder.setTake(resultSet.getBoolean("isTake"));
                preOrder.setMachineSerialNumber(resultSet.getString("machineSerialNumber"));
                preOrder.setUserId(resultSet.getString("userId"));

                preOrders.add(preOrder);
            }

            return preOrders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteOrderById() {

    }
}
