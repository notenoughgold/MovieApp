package com.altayiskender.movieapp.ui.bookmarks


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.altayiskender.movieapp.repository.Repository
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class BookmarksViewModelFactory(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BookmarksViewModel::class.java)) {
            Timber.d("creatingBookmarksViewModel")
            BookmarksViewModel(repository) as T
        } else {
            throw Throwable("ViewModel Not Found")
        }
    }
}