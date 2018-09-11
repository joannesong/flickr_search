package com.example.c4q.flickr_search;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.c4q.flickr_search.model.FlickrResponse;
import com.example.c4q.flickr_search.model.Photo;
import com.example.c4q.flickr_search.net.FlickrApi;
import com.example.c4q.flickr_search.net.RetrofitInstance;
import com.example.c4q.flickr_search.rv.FlickrAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    @BindView(R.id.search_edit_text)
    EditText searchEditText;
    @BindView(R.id.search_button)
    ImageButton searchButton;
    @BindView(R.id.recycler)
    RecyclerView searchRecyclerView;
    private String searchTerm;
    private ArrayList<Photo> savedPhotoList;
    private List<Photo> responsePhotoList;
    private FlickrAdapter flickrAdapter = new FlickrAdapter();
    private SearchPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setOrientation();
        if(savedInstanceState!=null || savedPhotoList != null){
            savedPhotoList = (ArrayList<Photo>) savedInstanceState.getSerializable("Rotated");
            Log.d("findmeser", String.valueOf(savedPhotoList));
            flickrAdapter.addPhotos(savedPhotoList);
            searchRecyclerView.setAdapter(flickrAdapter);
        }
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTerm = searchEditText.getText().toString();
                setNetwork();
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("Rotated", (Serializable) responsePhotoList);
        super.onSaveInstanceState(outState);
    }

    private void setNetwork() {
        RetrofitInstance retrofitInstance = new RetrofitInstance();
        FlickrApi flickrApi = retrofitInstance.get().create(FlickrApi.class);
        Call<FlickrResponse> photos = flickrApi.getPhotos(searchTerm);
        photos.enqueue(new Callback<FlickrResponse>() {
            @Override
            public void onResponse(@NonNull Call<FlickrResponse> call, @NonNull Response<FlickrResponse> response) {
                responsePhotoList = response.body().getPhotos().getPhoto();
                setRV(responsePhotoList);
            }
            @Override
            public void onFailure(Call<FlickrResponse> call, Throwable t) {
                ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                String failureStr;
                if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
                    failureStr = "No Internet Connection";
                }
                else{
                    failureStr = "Error";
                }
                Toast.makeText(getApplicationContext(), failureStr, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setRV(List<Photo> responsePhotoList) {
        flickrAdapter.addPhotos(responsePhotoList);
        searchRecyclerView.setAdapter(flickrAdapter);
    }

    private void setOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setSpanCount(2);
        } else {
            setSpanCount(3);
        }
    }

    private void setSpanCount(int spanCount) {
        GridLayoutManager gridLayoutManager;
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), spanCount);
        searchRecyclerView.setLayoutManager(gridLayoutManager);
    }
}
