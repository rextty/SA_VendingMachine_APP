package com.example.sa_vendingmachine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.sa_vendingmachine_app.databinding.ActivityFunctionalBinding;

public class FunctionalActivity extends AppCompatActivity {

    private ActivityFunctionalBinding UI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UI = ActivityFunctionalBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        UI.reserveButton.setOnClickListener(v -> {
            enterMapActivity();
        });

        UI.myOrderButton.setOnClickListener(v -> {
            enterMyOrderActivity();
        });
    }

    public void enterMapActivity() {
        Intent intent = new Intent();
        intent.setClass(FunctionalActivity.this, ReserveActivity.class);
        startActivity(intent);
    }

    public void enterMyOrderActivity() {
        Intent intent = new Intent();
        intent.setClass(FunctionalActivity.this, MyOrderActivity.class);
        startActivity(intent);
    }
}