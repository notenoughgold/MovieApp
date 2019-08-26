package com.altayiskender.movieapp.ui.popular


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.altayiskender.movieapp.repository.Repository
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class PopularViewModelFactory(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PopularViewModel::class.java)) {
            Timber.d("creatingPopularViewModel")
            PopularViewModel(repository) as T
        } else {
            throw Throwable("ViewModel Not Found")
        }
    }
}