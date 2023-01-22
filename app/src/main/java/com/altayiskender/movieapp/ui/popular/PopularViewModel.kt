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

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    val movies: Flow<PagingData<Movie>> =
        Pager(PagingConfig(pageSize = 20)) {
            MoviePagingSource(getPopularMoviesUseCase)
        }.flow.cachedIn(viewModelScope)

    var hasError = mutableStateOf(false)
    var isLoading = mutableStateOf(false)

}




