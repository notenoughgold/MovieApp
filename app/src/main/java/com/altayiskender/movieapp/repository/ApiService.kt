package com.altayiskender.movieapp.repository


import com.altayiskender.movieapp.BuildConfig
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.models.Movies
import com.altayiskender.movieapp.models.PeopleResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import timber.log.Timber
import java.util.*

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "api_key"
private const val LANGUAGE = "language"
private const val key = BuildConfig.TMDB_API_TOKEN


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

    companion object {

        operator fun invoke(): ApiService {

            val clientBuilder = OkHttpClient.Builder().addInterceptor(AuthInterceptor())
            if (BuildConfig.DEBUG) {
                clientBuilder.addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                })
            }
            val client = clientBuilder.build()
            Timber.d("http client created")
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build().create(ApiService::class.java)
        }


    }
}

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder().addQueryParameter(API_KEY, key).addQueryParameter(
            LANGUAGE, Locale.getDefault().language
        ).build()
        return chain.proceed(request.newBuilder().url(url).build())

    }
}