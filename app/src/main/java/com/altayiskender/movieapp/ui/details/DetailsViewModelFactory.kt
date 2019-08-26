package com.altayiskender.movieapp.ui.details


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.altayiskender.movieapp.repository.Repository
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            Timber.d("creatingDetailViewModel")
            DetailViewModel(repository) as T
        } else {
            throw Throwable("ViewModel Not Found")
        }
    }
}