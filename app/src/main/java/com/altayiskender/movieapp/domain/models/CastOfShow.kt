package com.altayiskender.movieapp.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CastOfShow(
    val id: Long,
    val character: String?,
    val name: String?,
    @SerializedName("profile_path")
    val profilePath: String?
) : Parcelable
