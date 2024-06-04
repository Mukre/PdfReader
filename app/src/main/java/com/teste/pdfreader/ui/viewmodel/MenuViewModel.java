package com.teste.pdfreader.ui.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;
import lombok.Setter;

@HiltViewModel
public class MenuViewModel extends ViewModel {
    @Getter
    MutableLiveData<String> gName = new MutableLiveData<>();
    @Getter
    MutableLiveData<Uri> gAvatar = new MutableLiveData<>();
    @Setter
    @Getter
    Boolean visible = false;
    @Getter
    GoogleSignInClient gClient;
    GoogleSignInOptions gOptions;
    GoogleSignInAccount gAccount;

    public MenuViewModel(){

    }

    public void initgOptions(Context context) {
        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        gClient = GoogleSignIn.getClient(context, gOptions);
        gAccount = GoogleSignIn.getLastSignedInAccount(context);
        gAvatar.setValue(gAccount.getPhotoUrl());

        if (gAccount != null) {
            getGName().setValue(gAccount.getEmail());
        }
    }

    public Bitmap createAvatar() {
        String personName = gAccount.getGivenName();
        String initials = personName != null && !personName.isEmpty() ? String.valueOf(personName.charAt(0)) : "?";
        int size = 100;
        int backgroundColor = Color.GRAY;
        int textColor = Color.WHITE;
        Bitmap bitmap = createInitialsBitmap(initials, size, backgroundColor, textColor);
        return bitmap;
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
