package com.altayiskender.movieapp.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private var movieLiveData = MutableLiveData<Movie>()
    var movieSavedStatusLiveData = MutableLiveData<Boolean>()
    var movieId: Long? = null
    var movieTitle: String? = null


    fun getMovieDetails(): MutableLiveData<Movie>? {
        if (movieId == null) {
            return null
        }
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getMovieDetails(movieId!!)

            withContext(Dispatchers.Main) {
                movieLiveData.value = result
            }

        }
        return movieLiveData
    }

    fun saveMovieToBookmarks() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertBookmarkedMovie(movieLiveData.value!!)
            withContext(Dispatchers.Main) {
                movieSavedStatusLiveData.value = true
            }
        }
    }

    fun deleteMovieFromBookmarks() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteBookmarkedMovie(movieLiveData.value!!)
            withContext(Dispatchers.Main) {
                movieSavedStatusLiveData.value = false
            }
        }
    }

    fun checkIfMovieSaved(movieId: Long): MutableLiveData<Boolean>? {
        CoroutineScope(Dispatchers.IO).launch {
            val n = repository.checkMovieIdIfSaved(movieId)
            withContext(Dispatchers.Main) {
                movieSavedStatusLiveData.value = n.isNotEmpty()
            }
        }
        return movieSavedStatusLiveData
    }
}