package com.altayiskender.movieapp.ui.popular

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.altayiskender.movieapp.data.remote.MoviePagingSource
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.usecases.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val SORT_POPULAR = 0
private const val SORT_UPCOMING = 1
private const val SORT_PLAYING = 2

@HiltViewModel
class PopularViewModel @Inject constructor(private val getPopularMoviesUseCase: GetPopularMoviesUseCase) :
    ViewModel() {

    var sortBy = SORT_POPULAR

    val movies: Flow<PagingData<Movie>> =
        Pager(PagingConfig(pageSize = 20)) {
            MoviePagingSource(getPopularMoviesUseCase)
        }.flow.cachedIn(viewModelScope)

    var hasError = mutableStateOf(false)
    var isLoading = mutableStateOf(false)

//    fun searchMovie(query: String) {
//        viewModelScope.launch {
//            try {
//                val result = repository.searchMovie(query)
//                movies.value = result.results
//            } catch (e: Throwable) {
//                Timber.d(e, "search $query movies error")
//                hasError.value = true
//
//            }
//
//        }
//    }

//    fun getHomepageMovies() {
//        hasError.value = false
//        viewModelScope.launch {
//            isLoading.value = true
//            try {
//                val result: Movies = when (sortBy) {
//                    SORT_POPULAR ->
//                        repository.getPopularMovies()
//                    SORT_PLAYING ->
//                        repository.getNowPlayingMovies()
//                    SORT_UPCOMING ->
//                        repository.getUpcomingMovies()
//                    else -> repository.getPopularMovies()
//                }
//                movies.value = result.results
//            } catch (e: Throwable) {
//                Timber.d(e, "get $sortBy movies error")
//                hasError.value = true
//            }
//            isLoading.value = false
//        }
//    }
}




