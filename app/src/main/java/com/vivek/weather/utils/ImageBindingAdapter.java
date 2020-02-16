package com.vivek.weather.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class ImageBindingAdapter {



    @BindingAdapter("weatherIcon")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view).load(imageUrl)
                .into(view);


    }
}
