package com.altayiskender.movieapp.repository


import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.models.Movies
import com.altayiskender.movieapp.models.PeopleResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(): Movies

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Movies

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Movies

    @GET("movie/{movie_id}?append_to_response=credits,videos")
    suspend fun getMovieDetails(@Path(value = "movie_id") movieId: Long): Movie

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): Movies

    @GET("person/{person_id}?append_to_response=movie_credits")
    suspend fun getPeopleDetails(@Path(value = "person_id") personId: Long): PeopleResponse
}