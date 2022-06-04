package com.example.sa_vendingmachine_app.Service;

import com.example.sa_vendingmachine_app.Model.DAO.UserDAO;
import com.example.sa_vendingmachine_app.Model.Entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public UserService() {}

    public User getUser(String token) {
        User user = new User();

        try {
            ResultSet resultSet = userDAO.getUserByToken(token);

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setToken(resultSet.getString("id_token"));
                user.setPermission(resultSet.getInt("permission"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet saveUser(User user) {
        return userDAO.saveUser(user);
    }
}
