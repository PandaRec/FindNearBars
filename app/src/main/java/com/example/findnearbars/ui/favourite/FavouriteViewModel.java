package com.example.findnearbars.ui.favourite;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.findnearbars.data.AppDatabase;
import com.example.findnearbars.pojo.FavouriteResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavouriteViewModel extends ViewModel {
    private static AppDatabase database;
    private LiveData<List<FavouriteResult>> favouriteResults;

    public void createFavouriteViewModel(Context context){
        database = AppDatabase.getInstance(context);
        favouriteResults = database.mainDao().getAllFavouriteResults();
    }

    public LiveData<List<FavouriteResult>> getFavouriteResults() {
        return favouriteResults;
    }



    public void insertFavouriteResult(FavouriteResult result){
        new InsertFavouriteResultTask().execute(result);
    }

    public FavouriteResult getFavouriteResultById(int favouriteResultId){
        try {
            return new GetFavouriteResultByIdTask().execute(favouriteResultId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  void deleteAllFavouriteResults(){
        new DeleteAllFavouriteResultsTask().execute();
    }

    public void deleteFavouriteResult(FavouriteResult favouriteResult){
        new DeleteFavouriteResultTask().execute(favouriteResult);
    }


    private static class InsertFavouriteResultTask extends AsyncTask<FavouriteResult,Void,Void>{
        @Override
        protected Void doInBackground(FavouriteResult... results) {
            if (results!=null && results.length>0){
                database.mainDao().insertFavouriteResult(results[0]);
            }
            return null;
        }
    }

    private static class GetFavouriteResultByIdTask extends AsyncTask<Integer,Void,FavouriteResult>{
        @Override
        protected FavouriteResult doInBackground(Integer... integers) {
            if(integers!=null && integers.length>0){
                return database.mainDao().getFavouriteResultById(integers[0]);
            }
            return null;
        }
    }

    private static class DeleteAllFavouriteResultsTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            database.mainDao().deleteAllFavouriteResults();
            return null;
        }
    }

    private static class DeleteFavouriteResultTask extends AsyncTask<FavouriteResult,Void,Void>{
        @Override
        protected Void doInBackground(FavouriteResult... favouriteResults) {
            if(favouriteResults!=null && favouriteResults.length>0)
                database.mainDao().deleteFavouriteResult(favouriteResults[0]);

            return null;
        }
    }
}
