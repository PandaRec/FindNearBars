package com.example.findnearbars;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.findnearbars.api.ApiFactory;
import com.example.findnearbars.api.ApiService;
import com.example.findnearbars.pojo.Response;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
//todo : override onCleared to delete loadData


public class MainViewModel extends AndroidViewModel {
    private CompositeDisposable compositeDisposable;
    private int page = 1;
    public MainViewModel(@NonNull Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
    }

    @SuppressLint("CheckResult") 
    public void loadData(int currentPage) {
        page= currentPage;
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        Disposable disposable = apiService.getResponseFromKudaGo(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Response>() {
            @Override
            public void accept(Response response) throws Exception {
                Log.i("my_ok", "ok"+page);
                if(response.getNext()!=null){
                    loadData(page+=1);
                }else {
                    Log.i("my_ok","load finished");
                    return;
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

}
