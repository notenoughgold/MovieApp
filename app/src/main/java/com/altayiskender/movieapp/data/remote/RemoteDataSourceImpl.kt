package com.altayiskender.movieapp.data.remote

import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.models.MoviesResponse
import com.altayiskender.movieapp.domain.models.PeopleResponse

class RemoteDataSourceImpl(private val apiService: ApiService) :
    RemoteDataSource {
    override suspend fun getPopularMovies(page: Int): MoviesResponse =
        apiService.getPopularMovies(page)

    override suspend fun getUpcomingMovies(): MoviesResponse = apiService.getUpcomingMovies()

    override suspend fun getNowPlayingMovies(): MoviesResponse = apiService.getNowPlayingMovies()

    override suspend fun getMovieDetails(id: Long): Movie = apiService.getMovieDetails(id)

    override suspend fun searchMovie(query: String): MoviesResponse = apiService.searchMovie(query)

    override suspend fun getPeopleDetails(id: Long): PeopleResponse =
        apiService.getPeopleDetails(id)
}