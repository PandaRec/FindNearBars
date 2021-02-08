package com.example.findnearbars.converters;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.findnearbars.pojo.Coords;
import com.example.findnearbars.pojo.Image;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ResultConverter {
    @TypeConverter
    public String coordsToString(Coords coords){
        return new Gson().toJson(coords);
    }
    @TypeConverter
    public Coords stringCoordsToCoords(String coords){
        return new Gson().fromJson(coords,Coords.class);
    }

    @TypeConverter
    public String imagesToString(List<Image> images){
        for(int i =0;i<images.size();i++){
            images.get(i).setSource(null);
        }
        return new Gson().toJson(images);
    }
    @TypeConverter
    public List<Image> imageStringToImages(String images){
        Gson gson = new Gson();
        ArrayList objects = gson.fromJson(images,ArrayList.class);
        ArrayList<Image> imagesArrayList = new ArrayList<>();
        for (Object o : objects){
            imagesArrayList.add(gson.fromJson(o.toString(),Image.class));
        }
        return imagesArrayList;
    }
}
