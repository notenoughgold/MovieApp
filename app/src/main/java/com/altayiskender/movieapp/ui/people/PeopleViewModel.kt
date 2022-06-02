package com.altayiskender.movieapp.ui.people

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altayiskender.movieapp.data.Repository
import com.altayiskender.movieapp.domain.models.PeopleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val peopleLiveData = MutableLiveData<PeopleResponse>()
    val isLoading = mutableStateOf<Boolean>(false)

    fun getPeopleDetails(id: Long) {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPeopleDetails(id)

            withContext(Dispatchers.Main) {
                peopleLiveData.value = result
            }
            isLoading.value = false
        }

    }

}