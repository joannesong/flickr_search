package com.example.c4q.flickr_search.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Photos implements Parcelable {
    private List<Photo> photo;

    protected Photos(Parcel in) {
        photo = in.createTypedArrayList(Photo.CREATOR);
    }

    public static final Creator<Photos> CREATOR = new Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel in) {
            return new Photos(in);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };

    public List<Photo> getPhoto() {
        return photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeTypedList(photo);
    }
}
