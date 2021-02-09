package com.example.findnearbars;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findnearbars.api.ApiFactory;
import com.example.findnearbars.api.ApiService;
import com.example.findnearbars.data.AppDatabase;
import com.example.findnearbars.pojo.Response;
import com.example.findnearbars.pojo.Result;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class MainViewModel extends AndroidViewModel {
    private CompositeDisposable compositeDisposable;
    private LiveData<List<Result>> results;
    private MutableLiveData<Boolean> isDownloadingFinished;
    private static AppDatabase database;
    private int page = 1;
    private MutableLiveData<Integer> counterForProgressBar;
    public MainViewModel(@NonNull Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
        database = AppDatabase.getInstance(application);
        results = database.mainDao().getAllResults();
        isDownloadingFinished = new MutableLiveData<>();
        counterForProgressBar = new MutableLiveData<>();

    }

    public void loadData(int currentPage) {
        page= currentPage;
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        Disposable disposable = apiService.getResponseFromKudaGo(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Response>() {
            @Override
            public void accept(Response response) throws Exception {
                Log.i("my_ok", "ok"+page);
                if(response.getNext()!=null){
                    counterForProgressBar.setValue(page);
                    if(page==1)
                        deleteAllResults();
                    insertListResults(response.getResults());
                    loadData(page+=1);
                }else {
                    Log.i("my_ok","load finished");
                    isDownloadingFinished.setValue(true);

                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("my_ex_MainViewModel", throwable.getMessage());
                return;
            }
        });
        compositeDisposable.add(disposable);



    }

    public MutableLiveData<Boolean> getIsDownloadingFinished() {
        return isDownloadingFinished;
    }

    public LiveData<List<Result>> getResults() {
        return results;
    }


    public MutableLiveData<Integer> getCounterForProgressBar() {
        return counterForProgressBar;
    }


    public void insertResult(Result result){
        new InsertResultTask().execute(result);
    }

    void insertListResults(List<Result> results){
        new InsertListResultsTask().execute(results);
    }

    void deleteAllResults(){
        new DeleteAllResultsTask().execute();
    }

    private static class InsertResultTask extends AsyncTask<Result,Void,Void>{
        @Override
        protected Void doInBackground(Result... results) {
            if(results!=null && results.length>0){
                database.mainDao().insertResult(results[0]);
            }
            return null;
        }
    }
    private static class InsertListResultsTask extends AsyncTask<List<Result>,Void,Void>{
        @Override
        protected Void doInBackground(List<Result>... lists) {
            if (lists!=null && lists.length>0){
                database.mainDao().insertListResults(lists[0]);
            }
            return null;
        }
    }
    private static class DeleteAllResultsTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            database.mainDao().deleteAllResults();
            return null;
        }
    }


    @Override
    protected void onCleared() {
        if(compositeDisposable!=null){
            compositeDisposable.dispose();
        }
    }
}
