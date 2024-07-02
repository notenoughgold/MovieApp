package com.altayiskender.movieapp.data.di

import com.altayiskender.movieapp.data.RepositoryImpl
import com.altayiskender.movieapp.data.local.LocalDataSource
import com.altayiskender.movieapp.data.local.LocalDataSourceImpl
import com.altayiskender.movieapp.data.remote.RemoteDataSource
import com.altayiskender.movieapp.data.remote.RemoteDataSourceImpl
import com.altayiskender.movieapp.domain.Repository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::LocalDataSourceImpl) { bind<LocalDataSource>() }
    singleOf(::RemoteDataSourceImpl) { bind<RemoteDataSource>() }
    singleOf(::RepositoryImpl) { bind<Repository>() }
}
