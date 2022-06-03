package com.example.sa_vendingmachine_app.Model.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExecuteSQL {

    private final ExecuteSQLRunnable executeSQLRunnable;

    public ExecuteSQL() {
        executeSQLRunnable = new ExecuteSQLRunnable();
    }

    public ExecuteSQL(String sql, int type) {
        executeSQLRunnable = new ExecuteSQLRunnable(sql, type);
    }

    public void execute() {
        new Thread(executeSQLRunnable).start();
        while (!executeSQLRunnable.getIsExecute()){}
    }

    public void setSql(String sql) {
        executeSQLRunnable.setSql(sql);

    }

    public void setType(int type) {
        executeSQLRunnable.setType(type);
    }

    public ResultSet getResultSet() {
        return executeSQLRunnable.getResultSet();
    }
}

class ExecuteSQLRunnable implements Runnable {

    private String url = "jdbc:mysql://192.168.3.104:3306/Vending_Machine?useUnicode=true&characterEncodeing=UTF-8&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrieval=true";
    private String username = "vendingmachine";
    private String password = "v1e2ndin4gma3ch6in1E";

    private Connection connection;

    private String sql;
    private int type;
    private boolean isExecuted;
    private ResultSet resultSet;

    public ExecuteSQLRunnable() {
        isExecuted = false;
    }

    public ExecuteSQLRunnable(String sql, int type) {
        this.sql = sql;
        this.type = type;
        isExecuted = false;
    }

    public void run() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username,password);

            switch (type) {
                case SQLExecuteTypeEnum.QUERY:
                    resultSet = connection.createStatement().executeQuery(sql);
                    break;
                case SQLExecuteTypeEnum.UPDATE:
                    connection.createStatement().executeUpdate(sql);
                    break;
            }

            isExecuted = true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public boolean getIsExecute() {
        return isExecuted;
    }
}
