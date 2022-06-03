package com.example.sa_vendingmachine_app.Service;

import com.example.sa_vendingmachine_app.Model.Entity.SalesRecord;

import java.sql.SQLException;

public class SalesRecordService {

    public SalesRecordService() {
    }

    public void addSalesRecord(SalesRecord salesRecord) {
        String query = String.format("INSERT INTO vending_machine.sales_record (productId, date) " +
                "VALUES (%s, '%s');", salesRecord.getProductId(), salesRecord.getDate()
        );
    }
}
