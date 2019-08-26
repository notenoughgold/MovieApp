package com.altayiskender.movieapp.ui.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.altayiskender.movieapp.models.PeopleResponse
import com.altayiskender.movieapp.repository.Repository
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PeopleViewModel(private val repository: Repository) : ViewModel() {

    private var peopleLiveData = MutableLiveData<PeopleResponse>()
    private val compositeDisposable = CompositeDisposable()
    var peopleId: Long? = null
    var peopleName: String? = null


    override fun onCleared() {
        compositeDisposable.clear()
    }

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