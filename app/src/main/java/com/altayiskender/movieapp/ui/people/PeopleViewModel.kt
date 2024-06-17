package com.altayiskender.movieapp.ui.people

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altayiskender.movieapp.domain.models.PeopleResponse
import com.altayiskender.movieapp.domain.usecases.GetPersonDetailUseCase
import com.altayiskender.movieapp.ui.NavigationPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val getPersonDetailUseCase: GetPersonDetailUseCase
) : ViewModel() {

    private val peopleId: Long = checkNotNull(stateHandle[NavigationPage.PeopleDetail.id])

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
                .onFailure {
                    Timber.e(it)
                }
            isLoading.value = false
        }
    }
}
