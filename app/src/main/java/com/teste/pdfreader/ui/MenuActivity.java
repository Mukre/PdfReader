package com.teste.pdfreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.teste.pdfreader.R;
import com.teste.pdfreader.databinding.ActivityMenuBinding;
import com.teste.pdfreader.ui.login.LoginActivity;
import com.teste.pdfreader.ui.viewmodel.MenuViewModel;

public class MenuActivity extends AppCompatActivity {
    private ActivityMenuBinding binding;
    private MenuViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this)
                .get(MenuViewModel.class);
        viewModel.initgOptions(getApplicationContext());
        binding = DataBindingUtil
                .setContentView(this, R.layout.activity_menu);
        binding.setViewModel(viewModel);

        binding.emailTextview.setText(viewModel.getGName().getValue());

        binding.logoutBtn.setOnClickListener(view -> {
            logout();
        });

        binding.actionBack.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, MainActivity.class));
        });

        binding.shareBtn.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, ShareActivity.class));
        });

        if (viewModel.getGAvatar().getValue() != null) {
            Glide.with(this)
                    .load(viewModel.getGAvatar().getValue())
                    .apply(new RequestOptions().centerCrop()
                            .error(R.drawable.google_logo))
                    .into(binding.avatarImgview);
        } else {
            binding.avatarImgview.setImageBitmap(viewModel.createAvatar());
        }

        binding.layout.setOnClickListener(v -> {
            if (viewModel.getVisible()) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_down);

                binding.emailBaloon.startAnimation(animation);
                binding.emailBaloon.setVisibility(View.GONE);
                binding.emailBaloon.setVisibility(View.GONE);
                viewModel.setVisible(!viewModel.getVisible());
            }
        });

        binding.avatarImgview.setOnClickListener(v -> {
            binding.emailBaloon.setVisibility(viewModel.getVisible() ? View.GONE : View.VISIBLE);

            int src = viewModel.getVisible() ? R.anim.pop_down : R.anim.pop_up;
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), src);
            binding.emailBaloon.startAnimation(animation);
            viewModel.setVisible(!viewModel.getVisible());
        });
    }

    public void logout() {
        viewModel.getGClient().signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

}