package altayiskender.movieapp.ui.people

import altayiskender.movieapp.models.PeopleResponse
import altayiskender.movieapp.repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PeopleViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

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
            val result = repository.getPeopleDetails(peopleId!!).await()

            withContext(Dispatchers.Main) {
                peopleLiveData.value = result
            }
        }
//        compositeDisposable.add(
//                repository.getPeopleDetails(peopleId!!)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .subscribeBy(
//                                onNext = {
//                                    peopleLiveData.value = it
//                                }
//                        )
//        )


        return peopleLiveData
    }

}