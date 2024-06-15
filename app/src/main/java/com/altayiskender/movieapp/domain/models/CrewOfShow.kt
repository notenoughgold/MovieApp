package com.altayiskender.movieapp.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CrewOfShow(
    val id: Long,
    val job: String?,
    val name: String?,
    @SerializedName("profile_path")
    val profilePath: String?
) : Parcelable
