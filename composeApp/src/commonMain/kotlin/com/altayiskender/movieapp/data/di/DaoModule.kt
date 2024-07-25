package com.altayiskender.movieapp.data.di

import com.altayiskender.movieapp.data.local.MovieBookmarksDao
import org.koin.dsl.module

val daoModule = module {
    single<MovieBookmarksDao> {
        get<DatabaseBuilderCreator>()
            .builder()
            .build()
            .movieBookmarksDao()
    }
}
