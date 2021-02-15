package com.example.findnearbars.pojo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.findnearbars.converters.ResultConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
@TypeConverters({ResultConverter.class})
@Entity(tableName = "items")
public class Result {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("timetable")
    @Expose
    private String timetable;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("body_text")
    @Expose
    private String bodyText;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("site_url")
    @Expose
    private String siteUrl;
    @SerializedName("foreign_url")
    @Expose
    private String foreignUrl;
    @SerializedName("coords")
    @Expose
    private Coords coords;
    @SerializedName("subway")
    @Expose
    private String subway;
    @SerializedName("favorites_count")
    @Expose
    private int favoritesCount;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    private double distance;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getForeignUrl() {
        return foreignUrl;
    }

    public void setForeignUrl(String foreignUrl) {
        this.foreignUrl = foreignUrl;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public String getSubway() {
        return subway;
    }

    public void setSubway(String subway) {
        this.subway = subway;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Result(int id, String title, String address, String timetable, String phone, String bodyText, String description, String siteUrl, String foreignUrl, Coords coords, String subway, int favoritesCount, List<Image> images, double distance) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.timetable = timetable;
        this.phone = phone;
        this.bodyText = bodyText;
        this.description = description;
        this.siteUrl = siteUrl;
        this.foreignUrl = foreignUrl;
        this.coords = coords;
        this.subway = subway;
        this.favoritesCount = favoritesCount;
        this.images = images;
        this.distance = distance;
    }
}
