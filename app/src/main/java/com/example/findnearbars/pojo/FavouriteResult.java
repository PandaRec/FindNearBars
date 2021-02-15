package com.example.findnearbars.pojo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.example.findnearbars.converters.ResultConverter;

import java.util.List;

@TypeConverters({ResultConverter.class})
@Entity(tableName = "favourite")
public class FavouriteResult extends Result{

    public FavouriteResult(int id, String title, String address, String timetable, String phone, String bodyText, String description, String siteUrl, String foreignUrl, Coords coords, String subway, int favoritesCount, List<Image> images, double distance){
        super(id, title, address, timetable, phone, bodyText, description, siteUrl, foreignUrl, coords, subway, favoritesCount,  images, distance);
    }

    @Ignore
    public FavouriteResult(Result currentResult) {
        super(currentResult.getId(),currentResult.getTitle(),currentResult.getAddress(),
                currentResult.getTimetable(),currentResult.getPhone(),currentResult.getBodyText(),
                currentResult.getDescription(),currentResult.getSiteUrl(),currentResult.getForeignUrl(),
                currentResult.getCoords(),currentResult.getSubway(),currentResult.getFavoritesCount(),
                currentResult.getImages(),currentResult.getDistance());
    }
}
