package com.altayiskender.movieapp.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var movieLiveData = MutableLiveData<Movie>()
    var movieSavedStatusLiveData = MutableLiveData<Boolean>()
    var movieId: Long? = null
    var movieTitle: String? = null


    fun getMovieDetails(): MutableLiveData<Movie>? {
        if (movieId == null) {
            return null
        }
        viewModelScope.launch {
            val result = repository.getMovieDetails(movieId!!)
            movieLiveData.postValue(result)
        }
        return movieLiveData
    }

    fun saveMovieToBookmarks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertBookmarkedMovie(movieLiveData.value!!)
                movieSavedStatusLiveData.postValue(true)
            }
        }
    }

    fun deleteMovieFromBookmarks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteBookmarkedMovie(movieLiveData.value!!)
                movieSavedStatusLiveData.postValue(false)
            }
        }
    }

    fun checkIfMovieSaved(movieId: Long): MutableLiveData<Boolean> {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val n = repository.checkMovieIdIfSaved(movieId)
                movieSavedStatusLiveData.postValue(n.isNotEmpty())
            }
        }
        return movieSavedStatusLiveData
    }
}