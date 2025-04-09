package com.altayiskender.movieapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.altayiskender.movieapp.data.di.daoModule
import com.altayiskender.movieapp.data.di.databaseBuilderModule
import com.altayiskender.movieapp.data.di.httpClientModule
import com.altayiskender.movieapp.data.di.repositoryModule
import com.altayiskender.movieapp.data.di.viewModelModule
import com.altayiskender.movieapp.theme.AppTheme
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.logger.Level
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AppComposable() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            KoinMultiplatformApplication(
                config = koinConfiguration {
                    modules(httpClientModule, databaseBuilderModule, daoModule, repositoryModule, viewModelModule)
                },
                logLevel = Level.DEBUG
            ) {
                NavHostPage()
            }
        }
    }
}
