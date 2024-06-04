package com.teste.pdfreader.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.teste.pdfreader.R;
import com.teste.pdfreader.databinding.ActivityHistoryBinding;
import com.teste.pdfreader.ui.MainActivity;

public class HistoryActivity extends AppCompatActivity {

    ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.actionBack.setOnClickListener(v -> {
            startActivity(new Intent(HistoryActivity.this, MainActivity.class));
        });
    }
}