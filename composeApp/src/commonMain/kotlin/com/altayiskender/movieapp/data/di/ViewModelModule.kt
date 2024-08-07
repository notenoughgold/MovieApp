package com.altayiskender.movieapp.data.di

import com.altayiskender.movieapp.domain.usecases.DeleteBookmarkUseCase
import com.altayiskender.movieapp.domain.usecases.GetBookmarkStatusUseCase
import com.altayiskender.movieapp.domain.usecases.GetBookmarksUseCase
import com.altayiskender.movieapp.domain.usecases.GetMovieDetailUseCase
import com.altayiskender.movieapp.domain.usecases.GetMoviesByKeywordUseCase
import com.altayiskender.movieapp.domain.usecases.GetPersonDetailUseCase
import com.altayiskender.movieapp.domain.usecases.GetPopularMoviesUseCase
import com.altayiskender.movieapp.domain.usecases.InsertBookmarkUseCase
import com.altayiskender.movieapp.ui.bookmarks.BookmarksViewModel
import com.altayiskender.movieapp.ui.details.DetailViewModel
import com.altayiskender.movieapp.ui.people.PeopleViewModel
import com.altayiskender.movieapp.ui.popular.PopularViewModel
import com.altayiskender.movieapp.ui.search.SearchViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    factoryOf(::GetPopularMoviesUseCase)
    factoryOf(::GetMovieDetailUseCase)
    factoryOf(::GetPersonDetailUseCase)
    factoryOf(::GetBookmarksUseCase)
    factoryOf(::InsertBookmarkUseCase)
    factoryOf(::DeleteBookmarkUseCase)
    factoryOf(::GetBookmarkStatusUseCase)
    factoryOf(::GetMoviesByKeywordUseCase)
    viewModelOf(::PopularViewModel)
    viewModelOf(::BookmarksViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::PeopleViewModel)
}
