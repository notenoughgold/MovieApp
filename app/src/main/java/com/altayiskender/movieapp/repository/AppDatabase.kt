package com.altayiskender.movieapp.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.altayiskender.movieapp.models.Movie

const val DATABASE_NAME = "MovieBookmarksDatabase"

@Database(entities = [Movie::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieBookmarksDao(): MovieBookmarksDao

//    companion object {
//
////        // For Singleton instantiation
////        @Volatile
////        private var instance: AppDatabase? = null
////
////        fun getInstance(context: Context): AppDatabase {
////            return instance ?: synchronized(this) {
////                instance ?: buildDatabase(context).also { instance = it }
////            }
////        }
////
////        // Create and pre-populate the database. See this article for more details:
////        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
//        private fun buildDatabase(context: Context): AppDatabase {
//            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//                .build()
//        }
//    }

}