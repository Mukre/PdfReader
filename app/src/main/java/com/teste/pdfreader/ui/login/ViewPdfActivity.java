package com.teste.pdfreader.ui.login;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.teste.pdfreader.R;
import com.teste.pdfreader.databinding.ActivityViewPdfBinding;

import java.io.File;

public class ViewPdfActivity extends AppCompatActivity {

    String filePath;
    ActivityViewPdfBinding binding;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        path = getIntent().getStringExtra("path");
        if (path != null) {
            loadPdf();
        } else {
            finish();
        }
    }

    private void loadPdf() {
        File file = new File(path);
        if (file.exists()) {
            binding.pdfView.fromFile(file)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(false)
                    .password(null)
                    .scrollHandle(null)
                    .load();

        } else {
            finish();
        }
    }
}