package com.example.sa_vendingmachine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sa_vendingmachine_app.Model.Entity.User;
import com.example.sa_vendingmachine_app.Service.UserService;
import com.example.sa_vendingmachine_app.databinding.ActivityFunctionalBinding;

public class FunctionalActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private ActivityFunctionalBinding UI;

    private UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UI = ActivityFunctionalBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        Intent intent = getIntent();
        String token = intent.getStringExtra("token");

        checkUser(token);

        UI.reserveButton.setOnClickListener(enterMapActivity);
        UI.myOrderButton.setOnClickListener(enterMyOrderActivity);
    }

    private void checkUser(String token) {
        token = token.substring(400, 420);

        User user = userService.getUser(token);

        if (user != null) {
            int permission = user.getPermission();

            if (permission == 0) {
                Log.e(TAG, "checkUser: Normal");
            }else {
                Log.e(TAG, "checkUser: Manager");
            }
        }else {
            User newUser = new User();
            newUser.setToken(token);
            newUser.setPermission(0);
            userService.saveUser(newUser);
        }
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