package com.example.findnearbars.ui.near;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findnearbars.data.AppDatabase;
import com.example.findnearbars.pojo.Coords;
import com.example.findnearbars.pojo.Result;
import com.google.android.material.resources.MaterialAttributes;

import java.util.ArrayList;
import java.util.List;

public class NearViewModel extends ViewModel {
    private AppDatabase database;
    private LiveData<List<Result>> results;

    public void createNearViewModel(Context context){
        database = AppDatabase.getInstance(context);
        results = database.mainDao().getAllResults();
    }

    public LiveData<List<Result>> getResults() {
        return results;
    }

    public List<Result> getResultsByDistance(Coords currentLocation, Coords finalLocation, int distance, List<Result> inputResults){
        int r = 6371000;
        ArrayList<Result> outputResults = new ArrayList<>();

        //1 градс = pi/180
        // E - долгота
        // N - широта
        //latitude - широта

        for(Result result : inputResults){

            double lat1InRad = Math.PI*currentLocation.getLat()/(180);
            double lon1InRad = Math.PI*currentLocation.getLon()/(180);

            double lat2InRad = Math.PI* result.getCoords().getLat()/(180);
            double lon2InRad = Math.PI*result.getCoords().getLon()/(180);

            double val1  = Math.pow(Math.sin((lat2InRad-lat1InRad)/2),2);
            double val2 = Math.cos(lat1InRad)*Math.cos(lat2InRad);
            double val3 = Math.pow(Math.sin((lon2InRad-lon1InRad)/2),2);

            double resultDistance = 2*r*Math.asin(Math.sqrt(val1+val2*val3));

            if(resultDistance<distance)
                outputResults.add(result);
        }




        return outputResults;
    }

}
