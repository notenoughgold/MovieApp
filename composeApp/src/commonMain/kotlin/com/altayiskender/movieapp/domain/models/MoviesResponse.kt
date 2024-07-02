package com.altayiskender.movieapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val results: List<Movie>,
    val page: Int
)
