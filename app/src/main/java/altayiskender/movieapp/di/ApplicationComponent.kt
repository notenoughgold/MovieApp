package altayiskender.movieapp.di

import altayiskender.movieapp.MovieApp
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    RepoModule::class,
    ActivityBindingModule::class,
    ViewModelModule::class

])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(movieApp: MovieApp)

}