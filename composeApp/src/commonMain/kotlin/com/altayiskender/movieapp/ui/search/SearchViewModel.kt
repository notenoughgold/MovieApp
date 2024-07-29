package com.altayiskender.movieapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.altayiskender.movieapp.data.remote.SearchMoviePagingSource
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.usecases.GetMoviesByKeywordUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

internal class SearchViewModel(
    private val getMoviesByKeywordUseCase: GetMoviesByKeywordUseCase
) : ViewModel() {

    private val _textInput: MutableStateFlow<String> = MutableStateFlow("")
    val textInput: StateFlow<String> = _textInput.asStateFlow()

    @OptIn(FlowPreview::class)
    private val effectiveSearchQuery = textInput
        .debounce(500L)
        .filter {
            it.length >= 3
        }

    private val pagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 40)

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: Flow<PagingData<Movie>> = effectiveSearchQuery.flatMapLatest {
        Pager(pagingConfig) {
            SearchMoviePagingSource(it, getMoviesByKeywordUseCase)
        }.flow
    }.cachedIn(viewModelScope)

    fun onTextInputChange(newInput: String) {
        viewModelScope.launch {
            _textInput.emit(newInput)
        }
    }

}
