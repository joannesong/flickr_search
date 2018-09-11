package com.example.c4q.flickr_search.net;

import com.example.c4q.flickr_search.model.FlickrResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by c4q on 9/10/18.
 */

public interface FlickrApi {

    String API_KEY = "698e1302856e60e61b87e5dd9d14f458";

    @GET("services/rest/?method=flickr.photos.search&api_key=" + API_KEY + "&safe_search=1&format=json&nojsoncallback=1")
    Call<FlickrResponse> getPhotos(@Query("text") String search);
}

