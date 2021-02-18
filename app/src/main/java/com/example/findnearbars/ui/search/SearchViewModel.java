package com.example.findnearbars.ui.search;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.findnearbars.data.AppDatabase;
import com.example.findnearbars.pojo.Result;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {
    private LiveData<List<Result>> results;
    private AppDatabase database;

//    public SearchViewModel() {
//        results = new MutableLiveData();
//    }

    public void createViewModel(Context context){
        database = AppDatabase.getInstance(context);
        results = database.mainDao().getAllResults();
        Log.i("f","f");
    }

    public LiveData<List<Result>> getResults() {
        return results;
    }

public List<Result> getResultsByName(List<Result> inputResults,String searchName){
        List<Result> outputResults = new ArrayList<>();
        for(Result result : inputResults){
            if(searchName!=null && !searchName.equals("") && result.getTitle().toLowerCase().startsWith(searchName.toLowerCase()) ){
                outputResults.add(result);
            }
        }
        return outputResults;
}
}
