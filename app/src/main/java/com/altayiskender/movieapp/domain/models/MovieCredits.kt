package com.altayiskender.movieapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MovieCredits(
    val cast: List<CastAsPerson>? = null,
    val crew: List<CrewAsPerson>? = null
)
