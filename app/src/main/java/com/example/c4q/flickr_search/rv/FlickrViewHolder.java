package com.example.c4q.flickr_search.rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.c4q.flickr_search.R;
import com.example.c4q.flickr_search.model.Photo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by c4q on 9/10/18.
 */

class FlickrViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.flickr_image)
    ImageView flickrImage;

    public FlickrViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(Photo photo, int position) {
        String imageURL = "https://farm" + photo.getFarm() + ".staticflickr.com/"
                + photo.getServer() + "/" + photo.getId() + "_" + photo.getSecret() + ".jpg";

        Picasso.get().load(imageURL).
                fit().
                centerCrop().
                placeholder(R.drawable.ic_image_black_24dp).
                error(R.drawable.ic_error).
                into(flickrImage);

    }


}
