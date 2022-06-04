package com.example.sa_vendingmachine_app.Model.DAO;

import com.example.sa_vendingmachine_app.Model.DBMgr;
import com.example.sa_vendingmachine_app.Model.Entity.PreOrder;
import com.example.sa_vendingmachine_app.Model.JDBC.SQLExecuteTypeEnum;

import java.sql.ResultSet;

public class PreOrderDAO {

    private DBMgr dbMgr = new DBMgr();

    public PreOrderDAO() {}


    public ResultSet getAllPreOrder() {
        return dbMgr.getAllPreOrder();
    }


    public ResultSet savePreOrderInformation(PreOrder preOrder) {
        return dbMgr.savePreOrderInformation(preOrder);
    }
}
