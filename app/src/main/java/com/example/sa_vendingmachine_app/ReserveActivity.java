package com.example.sa_vendingmachine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sa_vendingmachine_app.databinding.ActivityReserveBinding;

public class ReserveActivity extends AppCompatActivity {

    private ActivityReserveBinding UI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UI = ActivityReserveBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());


        UI.mapButton.setOnClickListener(v -> {
            enterMapActivity();
        });
    }

    public void enterMapActivity() {
        Intent intent = new Intent();
        intent.setClass(ReserveActivity.this, GoogleMapActivity.class);
        startActivity(intent);
    }
}