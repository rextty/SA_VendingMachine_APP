package com.example.sa_vendingmachine_app.Model.DAO;

import com.example.sa_vendingmachine_app.Model.DBMgr;

import java.sql.ResultSet;

public class MapDAO {

    private DBMgr dbMgr = new DBMgr();

    public MapDAO() {}

    public ResultSet getVendingMachineInformation() {
        return dbMgr.getMachineInformation();
    }
}
