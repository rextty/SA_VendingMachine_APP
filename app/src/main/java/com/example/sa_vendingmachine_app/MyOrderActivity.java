package com.example.sa_vendingmachine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sa_vendingmachine_app.Model.DBMgr;

public class MyOrderActivity extends AppCompatActivity {

    private DBMgr dbMgr = new DBMgr();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);


    }

    private void cancelOrder() {

    }

    private void queryOrderInfo() {
        dbMgr.getOrderPriceByQRCode();
    }
}