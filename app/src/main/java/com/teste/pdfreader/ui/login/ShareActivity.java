package com.teste.pdfreader.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.teste.pdfreader.R;
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