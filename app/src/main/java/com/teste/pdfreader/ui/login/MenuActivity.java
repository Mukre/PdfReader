package com.teste.pdfreader.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.teste.pdfreader.R;
import com.teste.pdfreader.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    GoogleSignInOptions gOptions;
    GoogleSignInClient gClient;
    ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(this, gOptions);

        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(gAccount != null) {
            String gName = gAccount.getDisplayName();
        }

        binding.logoutBtn.setOnClickListener(view -> {
            gClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    finish();
                    startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                }
            });
        });

        binding.actionBack.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, MainActivity.class));
        });

    }
}