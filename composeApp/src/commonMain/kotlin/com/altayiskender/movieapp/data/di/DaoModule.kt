package com.altayiskender.movieapp.data.di

import androidx.room.RoomDatabase
import com.altayiskender.movieapp.data.local.AppDatabase
import com.altayiskender.movieapp.data.local.MovieBookmarksDao
import org.koin.dsl.module

val daoModule = module {
    single<MovieBookmarksDao> {
        get<RoomDatabase.Builder<AppDatabase>>()
            .build()
            .movieBookmarksDao()
    }
}
