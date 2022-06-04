package com.example.sa_vendingmachine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sa_vendingmachine_app.databinding.ActivityFunctionalBinding;

public class FunctionalActivity extends AppCompatActivity {

    private ActivityFunctionalBinding UI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UI = ActivityFunctionalBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        UI.reserveButton.setOnClickListener(enterMapActivity);
        UI.myOrderButton.setOnClickListener(enterMyOrderActivity);
    }

    private View.OnClickListener enterMapActivity = v -> {
        Intent intent = new Intent();
        intent.setClass(FunctionalActivity.this, ReserveActivity.class);
        startActivity(intent);
    };

    private View.OnClickListener enterMyOrderActivity = v -> {
        Intent intent = new Intent();
        intent.setClass(FunctionalActivity.this, MyOrderActivity.class);
        startActivity(intent);
    };
}