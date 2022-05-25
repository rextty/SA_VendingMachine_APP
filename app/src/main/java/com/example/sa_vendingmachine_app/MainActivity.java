package com.example.sa_vendingmachine_app;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sa_vendingmachine_app.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding UI;

    private final String TAG = "Debugger ";

    private GoogleSignInOptions googleSignInOptions;

    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UI = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        // 綁定畫面
        bindViewListener();

        // 初始化Google登入
        initGoogleSignIn();

        // 檢查權限
        checkPermission();

//        googleSignInClient.signOut();
    }

    private void checkPermission() {
        List<String> permissionList = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)    // GPS
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);

        if (!permissionList.isEmpty())
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[0]), 1);
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
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, NavigationDrawerActivity.class);
                        startActivity(intent);

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