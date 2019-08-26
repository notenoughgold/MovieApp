package com.altayiskender.movieapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade


fun ImageView.loadImage(imageUrl: String) {
    if (imageUrl.isNotEmpty()) {
        Glide.with(this).load(imageUrl).transition(withCrossFade()).into(this)
    }
}