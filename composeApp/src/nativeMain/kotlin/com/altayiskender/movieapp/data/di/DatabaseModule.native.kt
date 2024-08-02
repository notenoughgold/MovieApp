package com.altayiskender.movieapp.data.di

import androidx.room.Room
import androidx.sqlite.driver.NativeSQLiteDriver
import com.altayiskender.movieapp.data.local.AppDatabase
import com.altayiskender.movieapp.data.local.instantiateImpl
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

actual val databaseBuilderModule: Module = module {
    single {
        Room.databaseBuilder<AppDatabase>(
            name = NSHomeDirectory() + "/bookmarks.db",
            factory = { AppDatabase::class.instantiateImpl() }
        ).setDriver(NativeSQLiteDriver())
    }
}
