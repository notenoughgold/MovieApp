package com.altayiskender.movieapp.ui.bookmarks

import androidx.lifecycle.ViewModel
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.usecases.GetBookmarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getBookmarksUseCase: GetBookmarksUseCase
) : ViewModel() {

    fun getAllBookmarkedMovies(): Flow<List<Movie>> {
        return getBookmarksUseCase.invoke()
    }

}
