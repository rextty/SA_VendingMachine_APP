package com.example.sa_vendingmachine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.sa_vendingmachine_app.Model.DBHandlerThread;
import com.example.sa_vendingmachine_app.Model.ExecuteSQL;
import com.example.sa_vendingmachine_app.Model.SQLExecuteTypeEnum;
import com.example.sa_vendingmachine_app.databinding.ActivityMainBinding;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Debugger";

    private ActivityMainBinding UI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UI = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        // debugger
        UI.accontEditTextText.setText("test");
        UI.passwordEditTextText.setText("test");

        // 綁定畫面
        bindViewListener();
    }

    private void bindViewListener() {
        UI.loginButton.setOnClickListener(v -> {
            String account = UI.accontEditTextText.getText().toString();

            if (account.isEmpty()) {
                Toast.makeText(this, "Account can't be empty", Toast.LENGTH_LONG).show();
                return;
            }

            ExecuteSQL checkAccount = new ExecuteSQL();
            checkAccount.setSql(String.format(
                    "SELECT * FROM vending_machine.user WHERE account = '%s';", account
            ));
            checkAccount.setType(SQLExecuteTypeEnum.QUERY);
            checkAccount.execute();

            try {
                if(checkAccount.getResultSet().next()) {
                    String password = UI.passwordEditTextText.getText().toString();

                    if (password.isEmpty()) {
                        Toast.makeText(this, "Password can't be empty", Toast.LENGTH_LONG).show();
                        return;
                    }

                    ExecuteSQL checkPassword = new ExecuteSQL();
                    checkPassword.setSql(String.format(
                            "SELECT password FROM vending_machine.user WHERE account = '%s';", account
                    ));
                    checkPassword.setType(SQLExecuteTypeEnum.QUERY);
                    checkPassword.execute();

                    if (checkPassword.getResultSet().next()) {
                        if (Objects.equals(checkPassword.getResultSet().getString("password"), password)) {
                            Log.e(TAG, "bindViewListener: in");
                        }else {
                            Toast.makeText(this, "Wrong password.", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(this, "Wrong password.", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(this, "Wrong account.", Toast.LENGTH_LONG).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}