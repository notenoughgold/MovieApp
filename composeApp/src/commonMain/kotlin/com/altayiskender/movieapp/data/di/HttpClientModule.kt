package com.altayiskender.movieapp.data.di

import androidx.compose.ui.text.intl.Locale
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private const val HOST = "api.themoviedb.org"
private const val PATH = "3/"
private const val API_KEY_NAME = "api_key"
private const val LANGUAGE = "language"
private const val LOG_TAG = "HTTP Client"

val httpClientModule = module {
    single {
        HttpClient {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = HOST
                    path(PATH)
                    parameters.append(API_KEY_NAME, API_KEY)
                    parameters.append(LANGUAGE, Locale.current.language)
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v(message = message, tag = LOG_TAG)
                    }
                }.also { Napier.base(DebugAntilog()) }
                level = LogLevel.ALL
            }
        }
    }
}
