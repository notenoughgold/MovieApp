package com.altayiskender.movieapp.ui.popular

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.models.Movies
import com.altayiskender.movieapp.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val SORT_POPULAR = 0
private const val SORT_UPCOMING = 1
private const val SORT_PLAYING = 2

class PopularViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    var sortBy = SORT_POPULAR
    val moviesLiveData = MutableLiveData<List<Movie>>()
    var hasError = MutableLiveData<Boolean>()

    init {
        getHomepageMovies()
    }

    fun searchMovie(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.searchMovie(query)
            withContext(Dispatchers.Main) {
                moviesLiveData.value = result.results
            }
        }
    }


    fun getHomepageMovies() {
        hasError.value = false
        CoroutineScope(Dispatchers.IO).launch {
            var result: Movies? = null
            try {
                when (sortBy) {
                    SORT_POPULAR ->
                        result = repository.getPopularMovies()
                    SORT_PLAYING ->
                        result = repository.getNowPlayingMovies()
                    SORT_UPCOMING ->
                        result = repository.getUpcomingMovies()
                }

                withContext(Dispatchers.Main) {
                    moviesLiveData.value = result?.results
                }
            } catch (e: Throwable) {
                Timber.d(e, "get $sortBy movies error")

                withContext(Dispatchers.Main) {
                    hasError.value = true
                }
            }
        }
    }
}




