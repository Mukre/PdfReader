package com.teste.pdfreader.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.teste.pdfreader.R;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        goToLogin();
    }

    public void goToLogin() {
        startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
        finish();
    }
}