package com.example.findnearbars.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.findnearbars.pojo.Result;

import java.util.List;

@Dao
public interface MainDao {

    @Query("select * from items")
    LiveData<List<Result>> getAllResults();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertResult(Result result);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListResults(List<Result> results);

    @Query("delete from items")
    void deleteAllResults();

}
