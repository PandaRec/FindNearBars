package com.example.findnearbars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.findnearbars.adapters.DetailImagesAdapter;
import com.example.findnearbars.pojo.Result;
import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {
    private Result currentResult;
    private RecyclerView recyclerViewImages;
    private DetailImagesAdapter adapter;

    private TextView textViewTitle;
    private TextView textViewAddress;
    private TextView textViewTimetable;
    private TextView textViewPhone;
    private TextView textViewSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewTimetable = findViewById(R.id.textViewTimetable);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewSite = findViewById(R.id.textViewSite);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("result")) {
            currentResult = new Gson().fromJson(intent.getStringExtra("result"), Result.class);
        } else {
            currentResult = null;
        }

        recyclerViewImages = findViewById(R.id.recyclerViewImages);
        adapter = new DetailImagesAdapter();
        recyclerViewImages.setAdapter(adapter);
        recyclerViewImages.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter.setResults(currentResult.getImages());
        setInformationToElements();

    }

    private void setInformationToElements() {
        textViewTitle.setText(currentResult.getTitle());
        textViewAddress.setText(currentResult.getAddress());
        textViewTimetable.setText(currentResult.getTimetable());
        textViewPhone.setText(currentResult.getPhone());
        textViewSite.setText(currentResult.getSiteUrl());
    }
}