package com.altayiskender.movieapp.di

import com.altayiskender.movieapp.MainActivity
import com.altayiskender.movieapp.ui.bookmarks.BookmarksFragment
import com.altayiskender.movieapp.ui.details.DetailFragment
import com.altayiskender.movieapp.ui.people.PeopleFragment
import com.altayiskender.movieapp.ui.popular.PopularFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun popularFragment(): PopularFragment

    @ContributesAndroidInjector
    abstract fun detailFragment(): DetailFragment

    @ContributesAndroidInjector
    abstract fun peopleFragment(): PeopleFragment

    @ContributesAndroidInjector
    abstract fun bookmarksFragment(): BookmarksFragment


}