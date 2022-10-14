package com.altayiskender.movieapp.data.di

import com.altayiskender.movieapp.data.RepositoryImpl
import com.altayiskender.movieapp.data.local.LocalDataSource
import com.altayiskender.movieapp.data.local.LocalDataSourceImpl
import com.altayiskender.movieapp.data.local.MovieBookmarksDao
import com.altayiskender.movieapp.data.remote.ApiService
import com.altayiskender.movieapp.data.remote.RemoteDataSource
import com.altayiskender.movieapp.data.remote.RemoteDataSourceImpl
import com.altayiskender.movieapp.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(movieBookmarksDao: MovieBookmarksDao): LocalDataSource {
        return LocalDataSourceImpl(movieBookmarksDao)
    }

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource
    ): Repository {
        return RepositoryImpl(localDataSource, remoteDataSource)
    }
}