package com.altayiskender.movieapp.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.altayiskender.movieapp.ui.bookmarks.BookmarksViewModel
import com.altayiskender.movieapp.ui.details.DetailViewModel
import com.altayiskender.movieapp.ui.people.PeopleViewModel
import com.altayiskender.movieapp.ui.popular.PopularViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PopularViewModel::class)
    abstract fun bindPopularViewModel(popularViewModel: PopularViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PeopleViewModel::class)
    abstract fun bindPeopleViewModel(peopleViewModel: PeopleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookmarksViewModel::class)
    abstract fun bindBookmarksViewModel(bookmarksViewModel: BookmarksViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory):ViewModelProvider.Factory

}