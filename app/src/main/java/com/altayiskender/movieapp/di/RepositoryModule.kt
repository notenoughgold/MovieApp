package com.altayiskender.movieapp.di

import com.altayiskender.movieapp.BuildConfig
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.altayiskender.movieapp.repository.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

private const val SERVER_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "api_key"
private const val LANGUAGE = "language"
private const val key = BuildConfig.TMDB_API_TOKEN

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {


    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor())
        if (BuildConfig.DEBUG) {
            client.addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        }
        return client.build()
    }

    @Provides
    @Singleton
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
    @Singleton
    fun providesAppDatabase(@ApplicationContext app: Context): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
           DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesMovieBookmarksDao(database: AppDatabase): MovieBookmarksDao {
        return database.movieBookmarksDao()
    }

    @Provides
    @Singleton
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