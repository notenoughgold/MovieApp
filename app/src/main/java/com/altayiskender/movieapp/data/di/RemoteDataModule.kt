package com.altayiskender.movieapp.data.di

import com.altayiskender.movieapp.BuildConfig
import com.altayiskender.movieapp.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import javax.inject.Singleton

private const val SERVER_URL = "https://api.themoviedb.org/3/"
private const val API_KEY_NAME = "api_key"
private const val LANGUAGE = "language"

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor())
        if (BuildConfig.DEBUG) {
            client.addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}

private class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url =
            request.url.newBuilder().addQueryParameter(API_KEY_NAME, BuildConfig.apiKey)
                .addQueryParameter(
                    LANGUAGE, Locale.getDefault().language
                ).build()
        return chain.proceed(request.newBuilder().url(url).build())
    }
}