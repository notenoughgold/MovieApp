package com.altayiskender.movieapp

import android.app.Application
import com.altayiskender.movieapp.config.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
open class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        plantTimber()
    }

    private fun plantTimber() {
        if (BuildConfig.isDebug) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
