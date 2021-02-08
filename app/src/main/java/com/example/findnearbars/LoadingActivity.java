package com.example.findnearbars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.icu.text.SearchIterator;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.findnearbars.converters.ResultConverter;
import com.example.findnearbars.pojo.Image;
import com.example.findnearbars.pojo.Result;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
//        Image image = new Image();
//        image.setImage("lolwerfwefwfwefew");
//        image.setSource(null);
//        ArrayList<Image>images = new ArrayList<>();
//        images.add(image);
//        image.setImage("lolwerfwefwfwefew");
//        image.setSource(null);
//        images.add(image);
//
//        ResultConverter converter = new ResultConverter();
//        String jsonImages = converter.imagesToString(images);
//        List<Image> imgs = converter.imageStringToImages(jsonImages);
//        Log.i("d","d");

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
                    Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}