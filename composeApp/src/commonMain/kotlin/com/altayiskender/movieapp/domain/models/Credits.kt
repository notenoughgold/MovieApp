package com.altayiskender.movieapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Credits(
    val cast: List<CastOfShow>?,
    val crew: List<CrewOfShow>?
)
