package com.altayiskender.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.altayiskender.movieapp.domain.models.Movie


@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(), ClearAllTables {

    override fun clearAllTables() {
        super.clearAllTables()
    }

    abstract fun movieBookmarksDao(): MovieBookmarksDao

}

// FIXME: Added a hack to resolve below issue:
// Class 'AppDatabase_Impl' is not abstract and does not implement abstract base class member 'clearAllTables'.
// https://issuetracker.google.com/issues/342905180
interface ClearAllTables {
    fun clearAllTables() {}
}
