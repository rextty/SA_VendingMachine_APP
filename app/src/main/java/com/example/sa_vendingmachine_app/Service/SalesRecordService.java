package com.example.sa_vendingmachine_app.Service;

import com.example.sa_vendingmachine_app.Model.DAO.SalesRecordDAO;
import com.example.sa_vendingmachine_app.Model.Entity.SalesRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesRecordService {

    private SalesRecordDAO salesRecordDAO = new SalesRecordDAO();

    public SalesRecordService() {}

    public ResultSet saveSalesRecord(SalesRecord salesRecord) {
        return salesRecordDAO.saveSalesRecord(salesRecord);
    }
}
