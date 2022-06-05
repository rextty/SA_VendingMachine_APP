package com.example.sa_vendingmachine_app.Model.DAO;

import android.annotation.SuppressLint;

import com.example.sa_vendingmachine_app.Model.Entity.SalesRecord;
import com.example.sa_vendingmachine_app.Model.JDBC.ExecuteSQL;
import com.example.sa_vendingmachine_app.Model.JDBC.SQLExecuteTypeEnum;

import java.sql.ResultSet;

public class SalesRecordDAO {

    private ExecuteSQL executeSQL = new ExecuteSQL();

    public SalesRecordDAO() {}

    @SuppressLint("DefaultLocale")
    public ResultSet saveSalesRecord(SalesRecord salesRecord) {
        String sql = String.format("INSERT INTO vending_machine.sales_record (productId, machineSerialNumber, date, userId) " +
                        "VALUES (%d, %d, '%s', %d);",
                salesRecord.getProductId(), salesRecord.getMachineSerialNumber(), salesRecord.getDate(), salesRecord.getUserId()
        );

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.UPDATE);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }
}
