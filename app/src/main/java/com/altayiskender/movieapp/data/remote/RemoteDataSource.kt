package com.altayiskender.movieapp.data.remote

import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.models.MoviesResponse
import com.altayiskender.movieapp.domain.models.PeopleResponse

interface RemoteDataSource {

    // Get a list of currently popular movies.
    suspend fun getPopularMovies(page: Int): MoviesResponse

    // Get a list of upcoming movie.
    suspend fun getUpcomingMovies(): MoviesResponse

    // Get a list of now playing movie.
    suspend fun getNowPlayingMovies(): MoviesResponse

    // Get details to the given movie.
    suspend fun getMovieDetails(id: Long): Movie

    // Search all movies for the given query.
    suspend fun searchMovie(query: String): MoviesResponse

    // Search details for the given people.
    suspend fun getPeopleDetails(id: Long): PeopleResponse
}