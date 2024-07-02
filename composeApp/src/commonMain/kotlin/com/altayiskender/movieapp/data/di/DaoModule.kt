package com.altayiskender.movieapp.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.altayiskender.movieapp.data.local.MovieBookmarksDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val daoModule = module {
    single<MovieBookmarksDao> {
        get<DatabaseBuilderCreator>()
            .builder()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
            .movieBookmarksDao()
    }
}
