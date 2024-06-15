package com.altayiskender.movieapp.data.remote


import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.models.MoviesResponse
import com.altayiskender.movieapp.domain.models.PeopleResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): MoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): MoviesResponse

    @GET("movie/{movie_id}?append_to_response=credits,videos")
    suspend fun getMovieDetails(@Path(value = "movie_id") movieId: Long): Movie

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): MoviesResponse

    @GET("person/{person_id}?append_to_response=movie_credits")
    suspend fun getPeopleDetails(@Path(value = "person_id") personId: Long): PeopleResponse
}
