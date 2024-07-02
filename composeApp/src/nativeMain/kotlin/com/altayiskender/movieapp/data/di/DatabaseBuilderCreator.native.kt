package com.altayiskender.movieapp.data.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.altayiskender.movieapp.data.local.AppDatabase
import com.altayiskender.movieapp.data.local.instantiateImpl
import platform.Foundation.NSHomeDirectory

actual class DatabaseBuilderCreator {
    actual fun builder(): RoomDatabase.Builder<AppDatabase> {
        return Room.databaseBuilder<AppDatabase>(
            name = NSHomeDirectory() + "/bookmarks.db",
            factory = { AppDatabase::class.instantiateImpl() }
        )
    }
}
