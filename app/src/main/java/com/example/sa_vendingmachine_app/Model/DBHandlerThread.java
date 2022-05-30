package com.example.sa_vendingmachine_app.Model;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHandlerThread extends HandlerThread implements Handler.Callback {

    private String url = "jdbc:mysql://192.168.3.104:3306/Vending_Machine?useUnicode=true&characterEncodeing=UTF-8&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrieval=true";
    private String username = "vendingmachine";
    private String password = "v1e2ndin4gma3ch6in1E";

    private ResultSet resultSet;
    private String sql;

    private Handler mWorkerHandler;
    private Handler mUIHandler;

    public DBHandlerThread(String name) {
        super(name);
    }

    public DBHandlerThread(String name, int priority) {
        super(name, priority);
    }

    public void setUIHandlerCallBack(Handler handler) {
        this.mUIHandler = handler;
    }

    public Handler getWorkerHandler() {
        return mWorkerHandler;
    }

    @Override
    protected void onLooperPrepared() {
        mWorkerHandler = new Handler(getLooper(), this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SQLExecuteTypeEnum.QUERY:
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, username,password);
                    resultSet = connection.createStatement().executeQuery(sql);
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
//                Message message = Message.obtain(null, 1);
//                mUIHandler.sendMessage(message);
                break;
        }
        return true;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }
}