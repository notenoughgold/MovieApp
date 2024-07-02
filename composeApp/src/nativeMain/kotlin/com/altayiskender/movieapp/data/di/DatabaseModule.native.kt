package com.altayiskender.movieapp.data.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val databaseBuilderModule: Module = module {
    singleOf(::DatabaseBuilderCreator)
}
