package com.altayiskender.movieapp.ui.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.models.Movies
import com.altayiskender.movieapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val SORT_POPULAR = 0
private const val SORT_UPCOMING = 1
private const val SORT_PLAYING = 2

@HiltViewModel
class PopularViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var sortBy = SORT_POPULAR
    val moviesLiveData = MutableLiveData<List<Movie>>()
    var hasError = MutableLiveData<Boolean>()

    init {
        getHomepageMovies()
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            try {
                val result = repository.searchMovie(query)
                moviesLiveData.value = result.results
            } catch (e: Throwable) {
                Timber.d(e, "search $query movies error")
                hasError.postValue(true)

            }

        }
    }


    fun getHomepageMovies() {
        hasError.value = false
        viewModelScope.launch {
            try {
                val result: Movies = when (sortBy) {
                    SORT_POPULAR ->
                        repository.getPopularMovies()
                    SORT_PLAYING ->
                        repository.getNowPlayingMovies()
                    SORT_UPCOMING ->
                        repository.getUpcomingMovies()
                    else -> repository.getPopularMovies()
                }
                moviesLiveData.value = result.results
            } catch (e: Throwable) {
                Timber.d(e, "get $sortBy movies error")
                hasError.value = true
            }
        }
    }
}




