package com.altayiskender.movieapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.altayiskender.movieapp.data.di.initKoin
import com.altayiskender.movieapp.theme.AppTheme
import com.altayiskender.movieapp.ui.NavHostPage

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    AppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHostPage()
        }
    }
}
