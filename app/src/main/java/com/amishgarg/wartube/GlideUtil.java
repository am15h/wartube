package com.amishgarg.wartube;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import retrofit2.http.GET;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GlideUtil {


    public static void loadImage(String url, ImageView imageView)
    {
        Context context = imageView.getContext();
        @SuppressLint("ResourceAsColor") ColorDrawable colorDrawable = new ColorDrawable(R.color.blue_grey_500);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(colorDrawable))
                .transition(withCrossFade())
                .into(imageView);
    }

    public static void loadImagePicasso(String url, ImageView imageView)
    {
        Context context = imageView.getContext();
        @SuppressLint("ResourceAsColor") ColorDrawable colorDrawable = new ColorDrawable(R.color.blue_grey_500);

        if(url!=null && url!="") {
            Picasso.get().load(url).into(imageView);
        }else
        {
            Picasso.get().load(R.color.blue_grey_500).into(imageView);
        }

    }
    public static void loadProfilePic(String url, ImageView imageView)
    {
        Context context = imageView.getContext();

        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_person_outline_black_24dp)
                        .dontAnimate()
                        .fitCenter())
                .into(imageView);

    }

}
