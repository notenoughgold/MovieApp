package com.altayiskender.movieapp.data.di

import com.altayiskender.movieapp.domain.Repository
import com.altayiskender.movieapp.domain.usecases.GetMovieDetailUseCase
import com.altayiskender.movieapp.domain.usecases.GetPersonDetailUseCase
import com.altayiskender.movieapp.domain.usecases.GetPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(repository: Repository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailUseCase(repository: Repository): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPersonDetailUseCase(repository: Repository): GetPersonDetailUseCase {
        return GetPersonDetailUseCase(repository)
    }
}