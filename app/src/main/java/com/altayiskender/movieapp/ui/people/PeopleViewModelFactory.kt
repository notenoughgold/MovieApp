package com.altayiskender.movieapp.ui.people


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.altayiskender.movieapp.repository.Repository
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class PeopleViewModelFactory(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PeopleViewModel::class.java)) {
            Timber.d("creatingPopularViewModel")
            PeopleViewModel(repository) as T
        } else {
            throw Throwable("ViewModel Not Found")
        }
    }
}