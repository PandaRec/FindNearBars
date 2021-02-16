package com.example.findnearbars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findnearbars.adapters.DetailImagesAdapter;
import com.example.findnearbars.pojo.FavouriteResult;
import com.example.findnearbars.pojo.Result;
import com.example.findnearbars.ui.favourite.FavouriteViewModel;
import com.example.findnearbars.ui.search.SearchViewModel;
import com.google.gson.Gson;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.util.List;

//todo : при нажатии на textViewPhone открыать тлф
//todo : изменить вывод url сайта на какую то кнопку
//todo: при нажатии на url открыть браузер
//todo : при нажатии на адрес открывать навигатор
// todo : при добавлении в избарнное изменить надпись
// todo: при удалении из избарнного изменить надпись

public class DetailsActivity extends AppCompatActivity {
    private Result currentResult;
    private RecyclerView recyclerViewImages;
    private DetailImagesAdapter adapter;
    private FavouriteViewModel favouriteViewModel;
    private FavouriteResult favouriteResult;

    private TextView textViewTitle;
    private TextView textViewAddress;
    private TextView textViewTimetable;
    private TextView textViewPhone;
    private TextView textViewSite;
    private CustomMapView mapview;
    private ScrollView mainScrollView;
    private ImageView imageViewFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("fefdbc18-9be6-468b-8dbc-6fb788c4f4a1");
        MapKitFactory.initialize(this);


        // Укажите имя activity вместо map.
        setContentView(R.layout.activity_details_2);
        //mapview = new CustomMapView(this);
        mapview = findViewById(R.id.mapview);
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


        textViewTitle = findViewById(R.id.textViewTitle);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewTimetable = findViewById(R.id.textViewTimetable);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewSite = findViewById(R.id.textViewSite);
        mainScrollView =  findViewById(R.id.scrollView);
        imageViewFavourite = findViewById(R.id.imageViewfavourite);

        favouriteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(FavouriteViewModel.class);
        favouriteViewModel.createFavouriteViewModel(this);
        setFavourite();

        imageViewFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favouriteResult==null){
                    favouriteViewModel.insertFavouriteResult(new FavouriteResult(currentResult));
                    Toast.makeText(DetailsActivity.this, "added", Toast.LENGTH_SHORT).show();

                }else {
                    favouriteViewModel.deleteFavouriteResult(new FavouriteResult(currentResult));
                    Toast.makeText(DetailsActivity.this, "removed", Toast.LENGTH_SHORT).show();
                }
                setFavourite();
            }
        });



        recyclerViewImages = findViewById(R.id.recyclerViewImages);
        adapter = new DetailImagesAdapter();
        recyclerViewImages.setAdapter(adapter);
        recyclerViewImages.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter.setResults(currentResult.getImages());
        setInformationToElements();


        recyclerViewImages.setBackgroundColor(getResources().getColor(R.color.gray));



        recyclerViewImages.setAdapter(adapter);

// add pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewImages);

        recyclerViewImages.addItemDecoration(new LinePagerIndicatorDecoration());
        favouriteViewModel.getFavouriteResults().observe(this, new Observer<List<FavouriteResult>>() {
            @Override
            public void onChanged(List<FavouriteResult> favouriteResults) {
                for(FavouriteResult result : favouriteResults){
                    Log.i("my_rez_favorite",result.getTitle());
                }
            }
        });

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
    private void setFavourite(){
        favouriteResult = favouriteViewModel.getFavouriteResultById(currentResult.getId());
        if(favouriteResult==null){
            imageViewFavourite.setImageResource(R.drawable.ic_add_to_favourite);

        }else {
            imageViewFavourite.setImageResource(R.drawable.ic_remove_from_favourite);

        }

    }

}
