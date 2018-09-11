package com.example.c4q.flickr_search.rv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.c4q.flickr_search.R;
import com.example.c4q.flickr_search.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c4q on 9/10/18.
 */

public class FlickrAdapter extends RecyclerView.Adapter<FlickrViewHolder> {
    private List<Photo> photoList= new ArrayList<>();

    public void addPhotos(List<Photo> newPhotos) {
        this.photoList.clear();
        this.photoList.addAll(newPhotos);
    }

    @NonNull
    @Override
    public FlickrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flickr_itemview, parent, false);
        return new FlickrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrViewHolder holder, int position) {
        holder.bind(photoList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
