package com.altayiskender.movieapp.data.remote

import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.models.MoviesResponse
import com.altayiskender.movieapp.domain.models.PeopleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class RemoteDataSourceImpl(private val httpClient: HttpClient) : RemoteDataSource {

    override suspend fun getPopularMovies(page: Int): MoviesResponse {
        return httpClient.get {
            url(path = "movie/popular")
            parameter("page", page)
        }.body()
    }

    override suspend fun getMovieDetails(id: Long): Movie {
        return httpClient.get {
            url(path = "movie/$id")
            parameter("append_to_response", "credits")
        }.body()
    }

    override suspend fun getPeopleDetails(id: Long): PeopleResponse {
        return httpClient.get {
            url(path = "person/$id")
            parameter("append_to_response", "movie_credits")
        }.body()
    }

    override suspend fun getMoviesByKeyword(keyword: String, page: Int): MoviesResponse {
        return httpClient.get {
            url(path = "search/movie")
            parameter("include_adult", false)
            parameter("page", page)
            parameter("query", keyword)
        }.body()
    }

}
