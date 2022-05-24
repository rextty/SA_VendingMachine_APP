package com.example.sa_vendingmachine_app;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.sa_vendingmachine_app.Model.DBHandlerThread;
import com.example.sa_vendingmachine_app.Model.ExecuteSQL;
import com.example.sa_vendingmachine_app.Model.SQLExecuteTypeEnum;
import com.example.sa_vendingmachine_app.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Debugger";

    private GoogleSignInOptions googleSignInOptions;

    private GoogleSignInClient googleSignInClient;

    private ActivityMainBinding UI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UI = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        // 綁定畫面
        bindViewListener();

        // 初始化Google登入
        initGoogleSignIn();

        googleSignInClient.signOut();
    }

    private void bindViewListener() {
        UI.loginButton.setOnClickListener(e ->{
            googleSignInActivityResultLauncher.launch(googleSignInClient.getSignInIntent());
        });
    }

    private void initGoogleSignIn() {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("360842512331-7jki9ch9k4ps27t3k07aei48lpmcappj.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    ActivityResultLauncher<Intent> googleSignInActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        String msg = "登入成功\nEmail："+account.getEmail()+"\nGoogle名稱：" + account.getDisplayName();
                        Log.e(TAG, msg);
                        Log.d(TAG, "Token: " + account.getIdToken());
                    } catch (ApiException e) {
                        Log.w(TAG, "Google sign in failed", e);
                    }
                }else {
                    Toast.makeText(this, "Sign in fail, please retry.", Toast.LENGTH_LONG).show();
                }
            }
    );
}