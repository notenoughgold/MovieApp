package com.altayiskender.movieapp

import android.app.Application
import com.altayiskender.movieapp.data.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MovieApp)
            androidLogger()
        }
    }
}
