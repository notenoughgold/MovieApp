package com.altayiskender.movieapp.models


data class MovieCredits(
    val cast: List<CastAsPerson?>? = null,
    val crew: List<CrewAsPerson?>? = null
)
