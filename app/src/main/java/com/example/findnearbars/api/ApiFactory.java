package com.example.findnearbars.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    //TODO : link to kudago site https://kudago.com/public-api/v1.4/places/?page=11&page_size=1&fields=title,address,timetable,phone,images,description,body_text,site_url,foreign_url,coords,subway,favorites_count&location=msk&categories=bar,brewery,clubs,restaurants,strip-club

    private static ApiFactory apiFactory;
    private static Retrofit retrofit;
    private static final String BASE_URL_KUDAGO="https://kudago.com/public-api/v1.4/";

    public ApiFactory(){
    retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL_KUDAGO).build();
    }

    public static ApiFactory getInstance(){
        if (apiFactory==null){
            apiFactory = new ApiFactory();
        }
        return apiFactory;
    }
    public ApiService getApiService(){
        return retrofit.create(ApiService.class);
    }
}
