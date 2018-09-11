package com.example.c4q.flickr_search;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c4q.flickr_search.model.Photo;
import com.example.c4q.flickr_search.net.RetrofitInstance;
import com.example.c4q.flickr_search.rv.FlickrAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    @BindView(R.id.search_edit_text)
    EditText searchEditText;
    @BindView(R.id.search_button)
    ImageButton searchButton;
    @BindView(R.id.recycler)
    RecyclerView searchRecyclerView;
    @BindView(R.id.empty_response_image)
    ImageView emptyResponseImage;
    @BindView(R.id.empty_response_text)
    TextView emptyResponseText;

    private String searchTerm;
    private FlickrAdapter flickrAdapter = new FlickrAdapter();
    private SearchPresenter presenter;

    private static final String PHOTO_LIST_SAVED_TO_BUNDLE = "rotated_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setAppBarTitle();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTerm = searchEditText.getText().toString();
                setNetwork();
            }
        });
    }
    private void setNetwork() {
        if(!searchTerm.isEmpty()){
            presenter = new SearchPresenter(this, new RetrofitInstance(), searchTerm);
            presenter.networkCall();
        }
        else{
            Toast.makeText(this, "Empty Search. Try Again.", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PHOTO_LIST_SAVED_TO_BUNDLE, (ArrayList<? extends Parcelable>) presenter.getPhotoList());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter = new SearchPresenter(this, savedInstanceState.<Photo>getParcelableArrayList(PHOTO_LIST_SAVED_TO_BUNDLE));
        presenter.showSavedList();
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

    @Override
    public void setAppBarTitle() {
        ActionBar actionBar = getSupportActionBar();
        String title = getResources().getString(R.string.search_appbar_title);
        actionBar.setTitle(title);
    }

    @Override
    public void showUserRV(List<Photo> responsePhotoList) {
        if(searchRecyclerView.getVisibility() == View.INVISIBLE){
            searchRecyclerView.setVisibility(View.VISIBLE);
            emptyResponseImage.setVisibility(View.INVISIBLE);
            emptyResponseText.setVisibility(View.INVISIBLE);
        }
        setOrientation();
        flickrAdapter.addPhotos(responsePhotoList);
        searchRecyclerView.setAdapter(flickrAdapter);
    }

    @Override
    public void showNetworkErrorMsg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.network_error_message);
        builder.setPositiveButton(R.string.network_error_retry, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                presenter.networkCall();
            }
        });
        builder.setNegativeButton(R.string.network_error_closeapp, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showNoResponseMsg() {
            emptyResponseImage.setVisibility(View.VISIBLE);
            emptyResponseText.setVisibility(View.VISIBLE);
            searchRecyclerView.setVisibility(View.INVISIBLE);
    }

}
