package altayiskender.movieapp.di


import altayiskender.movieapp.MainActivity
import altayiskender.movieapp.ui.Bookmarks.BookmarksFragment
import altayiskender.movieapp.ui.Details.DetailFragment
import altayiskender.movieapp.ui.People.PeopleFragment
import altayiskender.movieapp.ui.Popular.PopularFragment
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