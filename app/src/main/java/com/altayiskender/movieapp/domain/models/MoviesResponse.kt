package com.altayiskender.movieapp.domain.models

data class MoviesResponse(
    val results: List<Movie>,
    val page: Int
)