package com.altayiskender.movieapp.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.usecases.DeleteBookmarkUseCase
import com.altayiskender.movieapp.domain.usecases.GetBookmarkStatusUseCase
import com.altayiskender.movieapp.domain.usecases.GetMovieDetailUseCase
import com.altayiskender.movieapp.domain.usecases.InsertBookmarkUseCase
import com.altayiskender.movieapp.ui.NavigationPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    stateHandle: SavedStateHandle,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    getBookmarkStatusUseCase: GetBookmarkStatusUseCase,
    private val insertBookmarkUseCase: InsertBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {

    private val movieId: Long = checkNotNull(stateHandle[NavigationPage.MovieDetail.id])

    var movieState = mutableStateOf<Movie?>(null)

    var movieSavedState = getBookmarkStatusUseCase.invoke(movieId)

    var loading = mutableStateOf(false)

    init {
        getMovieDetails(movieId)
    }

    private fun getMovieDetails(id: Long) {
        viewModelScope.launch {
            loading.value = true
            getMovieDetailUseCase.invoke(id).onSuccess {
                movieState.value = it
            }
            loading.value = false
        }
    }

    private fun saveMovieToBookmarks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                movieState.value?.let {
                    insertBookmarkUseCase.invoke(it)
                }
            }
        }
    }

    private fun deleteMovieFromBookmarks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                movieState.value?.let {
                    deleteBookmarkUseCase.invoke(it)
                }
            }
        }
    }

    fun toggleBookmarkStatus(bookmarkedStatus: Boolean) {
        if (bookmarkedStatus) {
            deleteMovieFromBookmarks()
        } else {
            saveMovieToBookmarks()
        }
    }
}
