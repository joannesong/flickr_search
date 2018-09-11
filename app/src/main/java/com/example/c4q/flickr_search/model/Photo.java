package com.example.c4q.flickr_search.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable{
    private int farm;
    private String server, secret, id;

    private Photo(Parcel in) {
        farm = in.readInt();
        server = in.readString();
        secret = in.readString();
        id = in.readString();
    }

    static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public int getFarm() {
        return farm;
    }

    public String getServer() {
        return server;
    }

    public String getSecret() {
        return secret;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(server);
        parcel.writeString(secret);
        parcel.writeString(id);
        parcel.writeInt(farm);

    }
}
