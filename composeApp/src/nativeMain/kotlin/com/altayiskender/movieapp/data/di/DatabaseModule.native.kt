package com.altayiskender.movieapp.data.di

import androidx.room.Room
import androidx.sqlite.driver.NativeSQLiteDriver
import com.altayiskender.movieapp.data.local.AppDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual val databaseBuilderModule: Module = module {
    single {
        Room.databaseBuilder<AppDatabase>(
            name = documentDirectory() + "/bookmarks.db",
        ).setDriver(NativeSQLiteDriver())
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
