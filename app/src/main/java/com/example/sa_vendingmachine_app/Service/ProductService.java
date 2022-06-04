package com.example.sa_vendingmachine_app.Service;


import android.util.Log;

import com.example.sa_vendingmachine_app.Model.DAO.ProductDAO;
import com.example.sa_vendingmachine_app.Model.DBMgr;
import com.example.sa_vendingmachine_app.Model.Entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {

    private ProductDAO productDAO = new ProductDAO();

    public ProductService() {}

    public ArrayList<Product> getAllProduct() {
        ResultSet resultSet = productDAO.getProductInformation();

        try {
            ArrayList<Product> productList = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
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

    public Product getProductByProductId(int id) {

        try {
            ResultSet resultSet = productDAO.getProductByProductId(id);

            Product product = new Product();

            if (resultSet.next()) {
                product.setId(resultSet.getInt("id"));
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
