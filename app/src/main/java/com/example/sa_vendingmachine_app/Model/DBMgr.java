package com.example.sa_vendingmachine_app.Model;

import android.util.Log;

import com.example.sa_vendingmachine_app.Model.Entity.PreOrder;
import com.example.sa_vendingmachine_app.Model.Entity.Product;
import com.example.sa_vendingmachine_app.Model.JDBC.ExecuteSQL;
import com.example.sa_vendingmachine_app.Model.JDBC.SQLExecuteTypeEnum;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBMgr {

    private ExecuteSQL executeSQL = new ExecuteSQL();

    public DBMgr() {}

    public ResultSet getMachineInformation() {
        String sql = "SELECT id,name,ST_X(location),ST_Y(location),state FROM vending_machine.vending_machine;";

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.QUERY);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }

    public ResultSet getProductInformation() {
        String sql = "SELECT * FROM vending_machine.product;";

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.QUERY);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }

    public ResultSet getAllPreOrder() {
        String sql = "SELECT * FROM vending_machine.pre_order;";

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.QUERY);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }

    public ResultSet getProductByProductId(String productId) {
        String sql = "SELECT * FROM vending_machine.product WHERE `productId` = " + productId + ";";

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.QUERY);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }

    public ResultSet savePreOrderInformation(PreOrder preOrder) {
        String sql = String.format("INSERT INTO vending_machine.pre_order " +
                "(expireDate, isTake, qrcode) VALUES ('%s', %b, '%s');",
                preOrder.getExpireDate(), false, preOrder.getQrcode()
        );

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.UPDATE);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }

    public ResultSet getPreOrderInformationByUserId(String userId) {
        String sql = String.format("SELECT * FROM vending_machine.pre_order WHERE userId = %s;", userId);

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.QUERY);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }

    public ResultSet getTimeInformation() {
        String sql = "SELECT expireDate FROM vending_machine.pre_order;";

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.QUERY);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }

    public ResultSet getPreOrderByQRCode() {
        String sql = "SELECT * FROM vending_machine.pre_order;";

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.QUERY);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }

    public void getOrderPriceByQRCode() {

    }

}
