package com.altayiskender.movieapp.di

import com.altayiskender.movieapp.data.Repository
import com.altayiskender.movieapp.data.RepositoryImpl
import com.altayiskender.movieapp.data.local.LocalDataSource
import com.altayiskender.movieapp.data.local.LocalDataSourceImpl
import com.altayiskender.movieapp.data.remote.RemoteDataSource
import com.altayiskender.movieapp.data.remote.RemoteDataSourceImpl
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
    fun provideHotelsRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource {
        return remoteDataSource
    }

    @Provides
    @Singleton
    fun provideHotelsLocalDataSource(localDataSource: LocalDataSourceImpl): LocalDataSource {
        return localDataSource
    }

    @Provides
    @Singleton
    fun provideRepository(repository: RepositoryImpl): Repository {
        return repository
    }
}