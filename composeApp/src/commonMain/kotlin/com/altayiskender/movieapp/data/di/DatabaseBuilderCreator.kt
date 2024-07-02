package com.altayiskender.movieapp.data.di

import androidx.room.RoomDatabase
import com.altayiskender.movieapp.data.local.AppDatabase

expect class DatabaseBuilderCreator {
    fun builder(): RoomDatabase.Builder<AppDatabase>
}
