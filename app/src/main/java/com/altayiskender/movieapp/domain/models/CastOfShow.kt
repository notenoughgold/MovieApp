package com.altayiskender.movieapp.domain.models

import com.google.gson.annotations.SerializedName

data class CastOfShow(
    val id: Long,
    val character: String,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String
)