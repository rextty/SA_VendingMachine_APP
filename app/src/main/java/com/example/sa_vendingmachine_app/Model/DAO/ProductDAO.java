package com.example.sa_vendingmachine_app.Model.DAO;

import com.example.sa_vendingmachine_app.Model.DBMgr;
import com.example.sa_vendingmachine_app.Model.Entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {

    private DBMgr dbMgr = new DBMgr();

    public ProductDAO() {}

    public ResultSet getProductInformation() {
        return dbMgr.getProductInformation();
    }

    public ResultSet getProductByProductId(String productId) {
        return dbMgr.getProductByProductId(productId);
    }
}
