package com.teste.pdfreader.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.teste.pdfreader.databinding.ActivityShareBinding;

public class ShareActivity extends AppCompatActivity {

    ActivityShareBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShareBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.actionBack.setOnClickListener(v -> {
            startActivity(new Intent(ShareActivity.this, MenuActivity.class));
        });


    }
}