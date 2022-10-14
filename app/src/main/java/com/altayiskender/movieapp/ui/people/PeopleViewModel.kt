package com.altayiskender.movieapp.ui.people

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            getPersonDetailUseCase.invoke(id).onSuccess {
                peopleState.value = it
            }.onFailure {
                Timber.e(it)
            }
            isLoading.value = false
        }
    }
}