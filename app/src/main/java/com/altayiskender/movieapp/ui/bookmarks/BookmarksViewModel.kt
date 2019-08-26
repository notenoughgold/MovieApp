package com.altayiskender.movieapp.ui.bookmarks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarksViewModel(private val repository: Repository) : ViewModel() {

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