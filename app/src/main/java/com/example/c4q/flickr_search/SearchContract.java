package com.example.c4q.flickr_search;

import com.example.c4q.flickr_search.model.Photo;

import java.util.List;

/**
 * Created by c4q on 9/11/18.
 */

public interface SearchContract {
    interface View{
        void showUserRV(List<Photo> photoList);
        void showNetworkErrorMsg();
        void showNoResponseMsg();
    }

    interface Presenter{
        void startNetworkCall();
        void getPhotos();
        void showSavedlist();

    }
}
