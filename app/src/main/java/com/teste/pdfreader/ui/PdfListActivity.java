package com.teste.pdfreader.ui.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teste.pdfreader.R;
import com.teste.pdfreader.databinding.ActivityPdfListBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PdfListActivity extends AppCompatActivity {

    ActivityPdfListBinding binding;
    RecyclerView recyclerView;
    AdapterClass adapter;
    ArrayList<String> pdfFilesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermission();

        binding = ActivityPdfListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new AdapterClass(this, pdfFiles());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        binding.actionBack.setOnClickListener(v -> {
            startActivity(new Intent(PdfListActivity.this, MainActivity.class));
        });
    }

    private ArrayList<String> pdfFiles() {
        ContentResolver contentResolver = getContentResolver();

        String mime = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        String memeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf");
        String[] args = new String[]{memeType};
        String[] proj = {MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.DISPLAY_NAME};
        String sortingOrder = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";
        Cursor cursor = contentResolver.query(MediaStore.Files.getContentUri("external")
                , proj, mime, args, sortingOrder);
        ArrayList<String> pdfFiles = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);

                String path = cursor.getString(index);
                pdfFiles.add(path);
            }
            cursor.close();
        }
        return pdfFiles;
    }

    private void displayPdfs() {
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".pdf")) {
                    pdfFilesList.add(file.getName());
                    adapter.pdffiles.add(file.getName());
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
    }
}

class AdapterClass extends RecyclerView.Adapter<AdapterClass.AdapterViewholder> {

    Context context;
    List<String> pdffiles;

    public AdapterClass (Context context, List<String> pdffiles){
        this.context = context;
        this.pdffiles = pdffiles;
    }

    @NonNull
    @Override
    public AdapterViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.viewfiles_layout,parent,false);
        return new AdapterViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewholder holder, @SuppressLint("RecyclerView") int position) {
        String path = pdffiles.get(position);
        File pdfFile = new File(path);
        String filename = pdfFile.getName();

        holder.filename.setText(filename);

        holder.filename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewPdfActivity.class);
                intent.putExtra("path",path);
                context.startActivity(intent);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(pdffiles.get(position));
                Uri uri;

                uri = FileProvider.getUriForFile(context,"com.example.pdffiles.fileprovider",file);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent,"Share File: "));

            }
        });

    }

    @Override
    public int getItemCount() {
        return pdffiles.size();
    }

    static class AdapterViewholder extends RecyclerView.ViewHolder{

        TextView filename;
        ImageView share;
        public AdapterViewholder(@NonNull View itemView) {
            super(itemView);

            filename = itemView.findViewById(R.id.fileName);
            share = itemView.findViewById(R.id.shareFile);

        }
    }
}