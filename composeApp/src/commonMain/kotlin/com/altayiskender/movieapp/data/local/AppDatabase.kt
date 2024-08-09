package com.altayiskender.movieapp.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.altayiskender.movieapp.domain.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieBookmarksDao(): MovieBookmarksDao

}
