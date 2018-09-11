package com.example.c4q.flickr_search.net;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by c4q on 9/10/18.
 */

public class RetrofitInstance {
    private static final String BASE_URL = "https://api.flickr.com/";
    private Retrofit retrofit;

    public RetrofitInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    @NonNull
    public Retrofit get() {
        return retrofit;
    }
}
