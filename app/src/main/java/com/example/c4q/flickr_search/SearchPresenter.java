package com.example.c4q.flickr_search;

import com.example.c4q.flickr_search.model.FlickrResponse;
import com.example.c4q.flickr_search.model.Photo;
import com.example.c4q.flickr_search.net.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by c4q on 9/11/18.
 */

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View view;
    private RetrofitInstance service;
    private List<Photo> photoList = new ArrayList<>();
    private String searchTerm;

    SearchPresenter(SearchContract.View view, RetrofitInstance service, String searchTerm) {
        this.view = view;
        this.service = service;
        this.searchTerm = searchTerm;
    }
    SearchPresenter(SearchContract.View view, List<Photo> photoList) {
        this.view = view;
        this.photoList = photoList;
    }

    @Override
    public void networkCall() {
        Call<FlickrResponse> photos = service.get().getPhotos(searchTerm);
        photos.enqueue(new Callback<FlickrResponse>() {
            @Override
            public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {
                photoList = response.body().getPhotos().getPhoto();
                if(photoList.size() != 0){
                    view.showUserRV(photoList);
                } else {
                    view.showNoResponseMsg();
                }
            }
            @Override
            public void onFailure(Call<FlickrResponse> call, Throwable t) {
                view.showNetworkErrorMsg();
            }
        });
    }

    @Override
    public void showSavedList() {
        view.showUserRV(photoList);
    }

    @Override
    public List<Photo> getPhotoList() {
        return photoList;
    }


}
