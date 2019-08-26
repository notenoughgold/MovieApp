package com.altayiskender.movieapp


import android.app.Application
import com.altayiskender.movieapp.repository.ApiService
import com.altayiskender.movieapp.repository.AppDatabase
import com.altayiskender.movieapp.repository.MovieBookmarksDao
import com.altayiskender.movieapp.repository.Repository
import com.altayiskender.movieapp.ui.bookmarks.BookmarksViewModelFactory
import com.altayiskender.movieapp.ui.details.DetailsViewModelFactory
import com.altayiskender.movieapp.ui.people.PeopleViewModelFactory
import com.altayiskender.movieapp.ui.popular.PopularViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.Kodein.Companion.lazy
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import timber.log.Timber


class MovieApp : Application(), KodeinAware {
    override val kodein: Kodein = lazy {
        import(androidXModule(this@MovieApp))
        bind() from singleton { ApiService() }
        bind<AppDatabase>() with singleton { AppDatabase.getInstance(this@MovieApp) }
        bind<MovieBookmarksDao>() with singleton { instance<AppDatabase>().movieBookmarksDao() }
        bind() from singleton { Repository(instance(), instance()) }
        bind() from provider { PopularViewModelFactory(instance()) }
        bind() from provider { PeopleViewModelFactory(instance()) }
        bind() from provider { DetailsViewModelFactory(instance()) }
        bind() from provider { BookmarksViewModelFactory(instance()) }


    }

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