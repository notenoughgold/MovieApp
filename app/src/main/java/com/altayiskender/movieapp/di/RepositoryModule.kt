package com.altayiskender.movieapp.di

import com.altayiskender.movieapp.BuildConfig
import android.app.Application
import com.altayiskender.movieapp.repository.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

private const val SERVER_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "api_key"
private const val LANGUAGE = "language"

private const val key = BuildConfig.TMDB_API_TOKEN

@Module
class RepoModule {


    @Provides
    @Reusable
    fun providesOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor())
        if (BuildConfig.DEBUG) {
            client.addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        }
        return client.build()
    }

    @Provides
    @Reusable
    fun providesRetrofitClient(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Reusable
    fun providesAppDatabase(app: Application): AppDatabase {
        return AppDatabase.getInstance(app)
    }

    @Provides
    @Reusable
    fun providesMovieBookmarksDao(database: AppDatabase): MovieBookmarksDao {
        return database.movieBookmarksDao()
    }

    @Provides
    @Reusable
    fun provideRepository(dbApi: ApiService, movieBookmarksDao: MovieBookmarksDao): Repository {
        return Repository(dbApi, movieBookmarksDao)
    }

}


class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder().addQueryParameter(API_KEY, key).addQueryParameter(LANGUAGE, Locale.getDefault().language).build()
        return chain.proceed(request.newBuilder().url(url).build())

    }
}