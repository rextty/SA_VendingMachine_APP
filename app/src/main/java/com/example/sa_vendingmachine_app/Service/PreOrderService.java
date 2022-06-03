package com.example.sa_vendingmachine_app.Service;


import com.example.sa_vendingmachine_app.Model.DBMgr;
import com.example.sa_vendingmachine_app.Model.Entity.PreOrder;
import com.example.sa_vendingmachine_app.Model.Entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PreOrderService {

    private DBMgr dbMgr = new DBMgr();

    public PreOrderService() {}

    public void savePreOrder(PreOrder preOrder) {
        dbMgr.savePreOrderInformation(preOrder);
    }
}
