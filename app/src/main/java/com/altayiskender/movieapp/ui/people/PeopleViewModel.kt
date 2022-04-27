package com.altayiskender.movieapp.ui.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altayiskender.movieapp.models.PeopleResponse
import com.altayiskender.movieapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var peopleLiveData = MutableLiveData<PeopleResponse>()
    var peopleId: Long? = null
    var peopleName: String? = null

    fun getPeopleDetails(): MutableLiveData<PeopleResponse>? {
        if (peopleId == null) {
            return null
        }
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getPeopleDetails(peopleId!!)

            withContext(Dispatchers.Main) {
                peopleLiveData.value = result
            }
        }

        return peopleLiveData
    }

}