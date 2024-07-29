package com.altayiskender.movieapp.data.remote

import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.models.MoviesResponse
import com.altayiskender.movieapp.domain.models.PeopleResponse

interface RemoteDataSource {

    // Get a list of currently popular movies.
    suspend fun getPopularMovies(page: Int): MoviesResponse

    // Get details to the given movie.
    suspend fun getMovieDetails(id: Long): Movie

    // Search details for the given people.
    suspend fun getPeopleDetails(id: Long): PeopleResponse

    suspend fun getMoviesByKeyword(keyword: String, page: Int): MoviesResponse
}
