package com.altayiskender.movieapp


import com.altayiskender.movieapp.di.DaggerApplicationComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber


class MovieApp : DaggerApplication() {
    private val applicationInjector = DaggerApplicationComponent.builder().application(this).build()
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        plantTimber()
    }

    private fun plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}