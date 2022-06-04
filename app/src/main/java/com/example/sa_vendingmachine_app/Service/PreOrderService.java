package com.example.sa_vendingmachine_app.Service;


import com.example.sa_vendingmachine_app.Model.DAO.PreOrderDAO;
import com.example.sa_vendingmachine_app.Model.DBMgr;
import com.example.sa_vendingmachine_app.Model.Entity.PreOrder;
import com.example.sa_vendingmachine_app.Model.Entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PreOrderService {

    private PreOrderDAO preOrderDAO = new PreOrderDAO();

    public PreOrderService() {}

    public void savePreOrder(PreOrder preOrder) {
        preOrderDAO.savePreOrderInformation(preOrder);
    }

    public ArrayList<PreOrder> getAllPreOrder() {
        ArrayList<PreOrder> preOrders = new ArrayList<>();

        try {
            ResultSet resultSet = preOrderDAO.getAllPreOrder();

            while (resultSet.next()) {
                PreOrder preOrder = new PreOrder();

                preOrder.setExpireDate(resultSet.getString("expireDate"));
                preOrder.setTake(resultSet.getBoolean("isTake"));
                preOrder.setMachineSerialNumber(resultSet.getInt("machineSerialNumber"));
                preOrder.setUserId(resultSet.getInt("userId"));

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
