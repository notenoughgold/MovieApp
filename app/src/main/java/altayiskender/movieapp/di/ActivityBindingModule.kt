package altayiskender.movieapp.di


import altayiskender.movieapp.MainActivity
import altayiskender.movieapp.ui.bookmarks.BookmarksFragment
import altayiskender.movieapp.ui.details.DetailFragment
import altayiskender.movieapp.ui.people.PeopleFragment
import altayiskender.movieapp.ui.popular.PopularFragment
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