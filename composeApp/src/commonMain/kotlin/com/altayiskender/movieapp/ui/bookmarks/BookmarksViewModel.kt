package com.altayiskender.movieapp.ui.bookmarks

import androidx.lifecycle.ViewModel
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.usecases.GetBookmarksUseCase
import kotlinx.coroutines.flow.Flow

class BookmarksViewModel(
    private val getBookmarksUseCase: GetBookmarksUseCase
) : ViewModel() {

    fun getAllBookmarkedMovies(): Flow<List<Movie>> {
        return getBookmarksUseCase.invoke()
    }

}
