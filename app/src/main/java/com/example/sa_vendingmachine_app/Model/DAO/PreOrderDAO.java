package com.example.sa_vendingmachine_app.Model.DAO;

import android.annotation.SuppressLint;

import com.example.sa_vendingmachine_app.Model.DBMgr;
import com.example.sa_vendingmachine_app.Model.Entity.PreOrder;
import com.example.sa_vendingmachine_app.Model.JDBC.ExecuteSQL;
import com.example.sa_vendingmachine_app.Model.JDBC.SQLExecuteTypeEnum;
import com.example.sa_vendingmachine_app.Resource.MySQL;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreOrderDAO {

    private ExecuteSQL executeSQL = new ExecuteSQL();

    public PreOrderDAO() {}

    @SuppressLint("DefaultLocale")
    public ResultSet getPreOrderByUserId(int userId) {
         String sql = String.format(
                "SELECT * FROM vending_machine.pre_order WHERE userId = %d;", userId
        );

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

    @SuppressLint("DefaultLocale")
    public void savePreOrderInformation(PreOrder preOrder, InputStream in) {
        new Thread(()->{
            String sql = String.format("INSERT INTO vending_machine.pre_order " +
                            "(machineSerialNumber, expireDate, isTake, qrcode, userId, totalPrice) VALUES ('%s', '%s', %b, (?), %d, %d);",
                    preOrder.getMachineSerialNumber(), preOrder.getExpireDate(), false, preOrder.getUserId(), preOrder.getTotalPrice()
            );
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(MySQL.url, MySQL.username, MySQL.password);

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setBinaryStream(1, in);
                ps.executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public ResultSet getLatestId() {
        String sql = "SELECT MAX(id) FROM vending_machine.pre_order;";

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.QUERY);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }

    public ResultSet updatePreOrder(PreOrder preOrder) {
        String sql = String.format(
                "UPDATE vending_machine.pre_order isTake = %b ;", preOrder.getTake()
        );

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.UPDATE);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }
}
