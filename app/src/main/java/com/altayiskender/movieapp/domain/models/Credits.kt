package com.altayiskender.movieapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credits(
    val cast: List<CastOfShow>?,
    val crew: List<CrewOfShow>?
) : Parcelable
