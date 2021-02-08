package com.example.findnearbars.ui.search;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findnearbars.MainViewModel;
import com.example.findnearbars.data.AppDatabase;
import com.example.findnearbars.pojo.Result;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private LiveData<List<Result>> results;
    private AppDatabase database;

    public SearchViewModel() {
        results = new MutableLiveData();
    }
    public void createViewModel(Context context){
        database = AppDatabase.getInstance(context);
        results = database.mainDao().getAllResults();
        Log.i("f","f");
    }

    public LiveData<List<Result>> getResults() {
        return results;
    }

    public void setResults(LiveData<List<Result>> results) {
        this.results = results;
    }
}
