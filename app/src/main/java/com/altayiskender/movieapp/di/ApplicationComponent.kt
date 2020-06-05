package com.altayiskender.movieapp.di

import com.altayiskender.movieapp.MovieApp
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        RepoModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class

    ]
)
interface ApplicationComponent : AndroidInjector<MovieApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(movieApp: MovieApp)

}