package com.altayiskender.movieapp.models

import com.google.gson.annotations.SerializedName

data class CrewOfShow(
    val id: Long,
    val job: String,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String
)