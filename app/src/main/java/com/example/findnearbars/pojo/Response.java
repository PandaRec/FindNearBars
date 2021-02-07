package com.example.findnearbars.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("timetable")
    @Expose
    private String timetable;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("is_stub")
    @Expose
    private boolean isStub;
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
    @SerializedName("comments_count")
    @Expose
    private int commentsCount;
    @SerializedName("is_closed")
    @Expose
    private boolean isClosed;
    @SerializedName("categories")
    @Expose
    private List<String> categories = null;
    @SerializedName("short_title")
    @Expose
    private String shortTitle;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("age_restriction")
    @Expose
    private int ageRestriction;
    @SerializedName("disable_comments")
    @Expose
    private boolean disableComments;
    @SerializedName("has_parking_lot")
    @Expose
    private boolean hasParkingLot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public boolean isIsStub() {
        return isStub;
    }

    public void setIsStub(boolean isStub) {
        this.isStub = isStub;
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

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public boolean isIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public boolean isDisableComments() {
        return disableComments;
    }

    public void setDisableComments(boolean disableComments) {
        this.disableComments = disableComments;
    }

    public boolean isHasParkingLot() {
        return hasParkingLot;
    }

    public void setHasParkingLot(boolean hasParkingLot) {
        this.hasParkingLot = hasParkingLot;
    }
}
