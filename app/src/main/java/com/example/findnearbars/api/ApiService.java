package com.example.findnearbars.api;

import com.example.findnearbars.pojo.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("places/?page_size=100&fields=title,address,timetable,phone,images,description,body_text,site_url,foreign_url,coords,subway,favorites_count&location=msk&categories=bar,brewery,clubs,restaurants,strip-club")
    Observable<Response> getResponseFromKudaGo(@Query("page") int page);
}
