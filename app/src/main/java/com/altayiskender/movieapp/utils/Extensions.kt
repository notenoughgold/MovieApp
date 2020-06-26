package com.altayiskender.movieapp.utils

import android.widget.ImageView
import com.altayiskender.movieapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

fun ImageView.loadImage(imageUrl: String?) {
    Glide.with(this).load(imageUrl).fallback(R.drawable.ic_person).transition(withCrossFade())
        .into(this)
}

