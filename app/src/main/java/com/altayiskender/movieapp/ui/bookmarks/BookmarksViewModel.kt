package com.altayiskender.movieapp.ui.bookmarks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altayiskender.movieapp.data.Repository
import com.altayiskender.movieapp.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var bookmarksLiveData = MutableLiveData<List<Movie>>()


    fun getAllBookmarkedMovies(): MutableLiveData<List<Movie>>? {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getAllBookmarkedMovies()
            withContext(Dispatchers.Main) {
                bookmarksLiveData.value = result
            }
        }

        return bookmarksLiveData
    }

}