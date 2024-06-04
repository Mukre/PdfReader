package com.teste.pdfreader.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
    GoogleSignInAccount gAccount;
    Uri gAvatar;
    Boolean visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        gClient = GoogleSignIn.getClient(this, gOptions);
        gAccount =  GoogleSignIn.getLastSignedInAccount(this);
        gAvatar = gAccount.getPhotoUrl();



        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(gAccount != null) {
            String gName = gAccount.getDisplayName();
            binding.emailTextview.setText(gAccount.getEmail());
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

        binding.shareBtn.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, ShareActivity.class));
        });

        if (gAvatar != null) {
            Glide.with(this)
                    .load(gAvatar)
                    .apply(new RequestOptions().centerCrop()
                            .error(R.drawable.google_logo))
                    .into(binding.avatarImgview);
        }
        else {
            String personName = gAccount.getGivenName();
            String initials = personName != null && !personName.isEmpty() ? String.valueOf(personName.charAt(0)) : "?";
            int size = 100;
            int backgroundColor = Color.GRAY;
            int textColor = Color.WHITE;
            Bitmap bitmap = createInitialsBitmap(initials, size, backgroundColor, textColor);
            binding.avatarImgview.setImageBitmap(bitmap);
        }

        binding.layout.setOnClickListener(v -> {
            if (visible){
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_down);

                binding.emailBaloon.startAnimation(animation);
                binding.emailBaloon.setVisibility(View.GONE);
                binding.emailBaloon.setVisibility(View.GONE);
                visible = !visible;
            }
        });

        binding.avatarImgview.setOnClickListener(v -> {
            binding.emailBaloon.setVisibility(visible ? View.GONE : View.VISIBLE);

            int src = visible ? R.anim.pop_down : R.anim.pop_up;
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), src);
            binding.emailBaloon.startAnimation(animation);
            visible = !visible;
        });
    }

    private Bitmap createInitialsBitmap(String initials, int size, int backgroundColor, int textColor) {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(backgroundColor);
        canvas.drawCircle(size / 2, size / 2, size / 2, paint);

        paint.setColor(textColor);
        paint.setTextSize(size / 2);
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float x = size / 2;
        float y = size / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText(initials, x, y, paint);

        return bitmap;
    }

}