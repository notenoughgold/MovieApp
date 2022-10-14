package com.altayiskender.movieapp.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.usecases.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getMovieDetailUseCase: GetMovieDetailUseCase) :
    ViewModel() {

    var movieState = mutableStateOf<Movie?>(null)

    // var movieSavedStatusLiveData = MutableLiveData<Boolean>()
    var loading = mutableStateOf(false)

    fun getMovieDetails(id: Long) {
        viewModelScope.launch {
            loading.value = true
            getMovieDetailUseCase.invoke(id).onSuccess {
                movieState.value = it
            }.onFailure {
                Timber.e(it)
            }
            loading.value = false
        }
    }

//    fun saveMovieToBookmarks() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                repository.insertBookmarkedMovie(movieState.value!!)
//                movieSavedStatusLiveData.postValue(true)
//            }
//        }
//    }
//
//    fun deleteMovieFromBookmarks() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                repository.deleteBookmarkedMovie(movieState.value!!)
//                movieSavedStatusLiveData.postValue(false)
//            }
//        }
//    }
//
//    fun checkIfMovieSaved(movieId: Long): MutableLiveData<Boolean> {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                val n = repository.checkMovieIdIfSaved(movieId)
//                movieSavedStatusLiveData.postValue(n.isNotEmpty())
//            }
//        }
//        return movieSavedStatusLiveData
//    }
}