package com.altayiskender.movieapp.data.di

import android.content.Context
import androidx.room.Room
import com.altayiskender.movieapp.data.local.AppDatabase
import com.altayiskender.movieapp.data.local.MovieBookmarksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext app: Context): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesMovieBookmarksDao(database: AppDatabase): MovieBookmarksDao {
        return database.movieBookmarksDao()
    }

    companion object {
        const val DATABASE_NAME = "MovieBookmarksDatabase"
    }

}