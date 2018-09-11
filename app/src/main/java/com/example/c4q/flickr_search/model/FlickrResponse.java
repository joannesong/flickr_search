package com.example.c4q.flickr_search.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by c4q on 9/10/18.
 */

public class FlickrResponse implements Parcelable{
    private Photos photos;

    protected FlickrResponse(Parcel in) {
        photos = in.readParcelable(Photos.class.getClassLoader());
    }

    public static final Creator<FlickrResponse> CREATOR = new Creator<FlickrResponse>() {
        @Override
        public FlickrResponse createFromParcel(Parcel in) {
            return new FlickrResponse(in);
        }

        @Override
        public FlickrResponse[] newArray(int size) {
            return new FlickrResponse[size];
        }
    };

    public Photos getPhotos() {
        return photos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(photos, flags);
    }
}
