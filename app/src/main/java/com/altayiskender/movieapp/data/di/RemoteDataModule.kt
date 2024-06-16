package com.altayiskender.movieapp.data.di

import com.altayiskender.movieapp.config.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.Locale
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Provides
    @Singleton
    fun providesKtorClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = HOST
                    path(PATH)
                    parameters.append(API_KEY_NAME, API_KEY)
                    parameters.append(LANGUAGE, Locale.getDefault().language)
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            if (BuildConfig.isDebug) {
                install(Logging) {
                    logger = Logger.ANDROID
                    level = LogLevel.ALL
                }
            }
        }
    }

    companion object {
        private const val HOST = "api.themoviedb.org"
        private const val PATH = "3/"
        private const val API_KEY_NAME = "api_key"
        private const val LANGUAGE = "language"
    }
}
