package com.example.findnearbars.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.findnearbars.pojo.FavouriteResult;
import com.example.findnearbars.pojo.Result;

@Database(entities = {Result.class, FavouriteResult.class},version = 6,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase database;
    private static final Object LOCK = new Object();

    public static AppDatabase getInstance(Context context){
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, AppDatabase.class, "items.db")
                        .fallbackToDestructiveMigration().build();
            }
            return database;
        }
    }
    public abstract MainDao mainDao();
}
