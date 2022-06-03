package com.example.sa_vendingmachine_app.Service;


import com.example.sa_vendingmachine_app.Model.DBMgr;
import com.example.sa_vendingmachine_app.Model.Entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//TODO:功能內聚
public class ProductService {

    private DBMgr dbMgr = new DBMgr();

    public ProductService() {}

    public ArrayList<Product> getAllProduct() {
        ResultSet resultSet = dbMgr.getProductInformation();

        try {
            ArrayList<Product> productList = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getString("productId"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setPrice(resultSet.getInt("price"));
                product.setName(resultSet.getString("name"));
                product.setImage(resultSet.getString("image"));

                productList.add(product);
            }

            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
