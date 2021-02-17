package com.example.findnearbars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SearchIterator;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.example.findnearbars.converters.ResultConverter;
import com.example.findnearbars.pojo.Image;
import com.example.findnearbars.pojo.Result;

import java.util.ArrayList;
import java.util.List;
// todo : изменить цвет активити
public class LoadingActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String APP_PREFERENCES = "settings";
    private static final String APP_PREFERENCES_IS_DOWNLOADED = "isDownloaded";
    private Boolean isDownloaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);


        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);


        if(sharedPreferences.contains(APP_PREFERENCES_IS_DOWNLOADED)) {
            isDownloaded = sharedPreferences.getBoolean(APP_PREFERENCES_IS_DOWNLOADED,false);
        }
        if(isDownloaded){
            Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
            startActivity(intent);
        }else {


            ProgressBar progressBar = findViewById(R.id.progressBarHorizontal);
            progressBar.setMax(10);
            progressBar.setProgress(0);


            MainViewModel mainViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MainViewModel.class);
//        mainViewModel.getResults().observe(this, new Observer<List<Result>>() {
//            @Override
//            public void onChanged(List<Result> results) {
//                Log.i("my_res","ok");
//            }
//        });
            mainViewModel.loadData(1);

            mainViewModel.getIsDownloadingFinished().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(aBoolean){
                        // если данные были сохранены - сохраняем значение для этого
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(APP_PREFERENCES_IS_DOWNLOADED, true);
                        editor.apply();

                        Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
            mainViewModel.getCounterForProgressBar().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    progressBar.setProgress(integer);

                }
            });

        }

    }


}