package com.example.sa_vendingmachine_app.Model.DAO;

import com.example.sa_vendingmachine_app.Model.DBMgr;
import com.example.sa_vendingmachine_app.Model.Entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {

    private DBMgr dbMgr = new DBMgr();

    public ProductDAO() {}

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

    public Product getProductByProductId(String productId) {

        try {
            ResultSet resultSet = dbMgr.getProductByProductId(productId);

            Product product = new Product();

            if (resultSet.next()) {
                product.setProductId(resultSet.getString("productId"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setPrice(resultSet.getInt("price"));
                product.setName(resultSet.getString("name"));
                product.setImage(resultSet.getString("image"));

                return product;
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
