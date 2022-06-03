package com.altayiskender.movieapp.ui.people

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altayiskender.movieapp.data.Result
import com.altayiskender.movieapp.domain.models.PeopleResponse
import com.altayiskender.movieapp.domain.usecases.GetPersonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(private val getPersonDetailUseCase: GetPersonDetailUseCase) :
    ViewModel() {

    val peopleState = mutableStateOf<PeopleResponse?>(null)
    val isLoading = mutableStateOf(false)

    fun getPeopleDetails(id: Long) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = getPersonDetailUseCase.invoke(id)) {
                is Result.Success -> {
                    peopleState.value = result.data
                }
                is Result.Error -> {
                    Timber.e(result.throwable)
                }
            }
            isLoading.value = false
        }
    }
}