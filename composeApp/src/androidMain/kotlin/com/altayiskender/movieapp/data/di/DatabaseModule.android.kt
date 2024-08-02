package com.altayiskender.movieapp.data.di

import android.content.Context
import androidx.room.Room
import com.altayiskender.movieapp.data.local.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseBuilderModule: Module = module {
    single {
        val context = get<Context>()
        Room.databaseBuilder<AppDatabase>(
            context = context,
            name = context.getDatabasePath("bookmarks.db").absolutePath,
        )
    }
}
