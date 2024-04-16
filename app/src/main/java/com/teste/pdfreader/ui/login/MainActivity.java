package com.teste.pdfreader.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.teste.pdfreader.R;
import com.teste.pdfreader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomTriangle.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_slide);
        binding.bottomTriangle.startAnimation(animation);

        binding.topTriangle.setVisibility(View.VISIBLE);
        Animation animationb = AnimationUtils.loadAnimation(this, R.anim.fade_slide);
        binding.topTriangle.startAnimation(animationb);


        binding.bottomTriangle.startAnimation(animation);

        binding.menuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });
        binding.openPdfBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, PdfListActivity.class));
        });

    }
}