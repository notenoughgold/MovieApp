package com.altayiskender.movieapp.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.altayiskender.movieapp.data.local.AppDatabase

actual class DatabaseBuilderCreator(private val context: Context) {
    actual fun builder(): RoomDatabase.Builder<AppDatabase> {
        return Room.databaseBuilder<AppDatabase>(
            context = context,
            name = context.getDatabasePath("bookmarks.db").absolutePath,
        )
    }
}
