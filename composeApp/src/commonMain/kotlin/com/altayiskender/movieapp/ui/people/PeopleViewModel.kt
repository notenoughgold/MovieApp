package com.altayiskender.movieapp.ui.people

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altayiskender.movieapp.domain.models.PeopleResponse
import com.altayiskender.movieapp.domain.usecases.GetPersonDetailUseCase
import com.altayiskender.movieapp.ui.NavigationRoute
import kotlinx.coroutines.launch

class PeopleViewModel(
    stateHandle: SavedStateHandle,
    private val getPersonDetailUseCase: GetPersonDetailUseCase
) : ViewModel() {

    private val peopleId: Long = checkNotNull(stateHandle[NavigationRoute.ParametricRoute.PeopleDetail.argumentName])

    val peopleState = mutableStateOf<PeopleResponse?>(null)
    val isLoading = mutableStateOf(false)

    init {
        getPeopleDetails(peopleId)
    }

    private fun getPeopleDetails(id: Long) {
        viewModelScope.launch {
            isLoading.value = true
            getPersonDetailUseCase.invoke(id)
                .onSuccess {
                    peopleState.value = it
                }
            isLoading.value = false
        }
    }
}
