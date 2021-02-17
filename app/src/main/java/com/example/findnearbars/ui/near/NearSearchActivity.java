package com.example.findnearbars.ui.near;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.findnearbars.R;
import com.example.findnearbars.pojo.Coords;

public class NearSearchActivity extends AppCompatActivity {
    private Coords coords = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_search);
        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra("coords")){
            double[] temp = intent.getDoubleArrayExtra("coords");
            coords.setLat(temp[0]);
            coords.setLon(temp[1]);
        }
        if(coords==null){
            startActivity(new Intent());
        }

    }
}