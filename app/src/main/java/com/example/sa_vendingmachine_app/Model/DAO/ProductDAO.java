package com.example.sa_vendingmachine_app.Model.DAO;

import com.example.sa_vendingmachine_app.Model.JDBC.ExecuteSQL;
import com.example.sa_vendingmachine_app.Model.JDBC.SQLExecuteTypeEnum;

import java.sql.ResultSet;

public class ProductDAO {

    private final ExecuteSQL executeSQL = new ExecuteSQL();

    public ProductDAO() {}

    public ResultSet getProductInformation() {
        String sql = "SELECT * FROM vending_machine.product;";

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
}
