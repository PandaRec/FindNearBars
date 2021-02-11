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
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;


public class DetailsActivity extends AppCompatActivity {
    private Result currentResult;
    private RecyclerView recyclerViewImages;
    private DetailImagesAdapter adapter;

    private TextView textViewTitle;
    private TextView textViewAddress;
    private TextView textViewTimetable;
    private TextView textViewPhone;
    private TextView textViewSite;
    private MapView mapview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("fefdbc18-9be6-468b-8dbc-6fb788c4f4a1");
        MapKitFactory.initialize(this);

        // Укажите имя activity вместо map.
        setContentView(R.layout.activity_details);
        mapview = (MapView)findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("result")) {
            currentResult = new Gson().fromJson(intent.getStringExtra("result"), Result.class);
        } else {
            currentResult = null;
        }

        Point point = new Point(currentResult.getCoords().getLat(),currentResult.getCoords().getLon());
        mapview.getMap().getMapObjects().addPlacemark(point);

        mapview.getMap().move(
                new CameraPosition(point, 15.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        mapview.setClickable(true);
        mapview.setFocusable(true);
        mapview.setDuplicateParentStateEnabled(false);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewTimetable = findViewById(R.id.textViewTimetable);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewSite = findViewById(R.id.textViewSite);



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
    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }
}
