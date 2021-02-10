package com.example.findnearbars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.findnearbars.adapters.DetailImagesAdapter;
import com.example.findnearbars.pojo.Result;
import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {
    private Result currentResult;
    private RecyclerView recyclerViewImages;
    private DetailImagesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra("result")){
            currentResult = new Gson().fromJson(intent.getStringExtra("result"),Result.class);
        }else {
            currentResult=null;
        }

        recyclerViewImages = findViewById(R.id.recyclerViewImages);
        adapter = new DetailImagesAdapter();
        recyclerViewImages.setAdapter(adapter);
        recyclerViewImages.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        adapter.setResults(currentResult.getImages());

    }

//    private void setInformationToElements(){
//
//    }
}