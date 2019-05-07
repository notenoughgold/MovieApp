package altayiskender.movieapp.ui.Popular

import altayiskender.movieapp.Pojos.Movie
import altayiskender.movieapp.Pojos.Movies
import altayiskender.movieapp.Repository.Repository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val SORT_POPULAR = 0
private const val SORT_UPCOMING = 1
private const val SORT_PLAYING = 2

class PopularViewModel
@Inject
constructor(private val repository: Repository) : ViewModel() {

    var sortBy = SORT_POPULAR
    val moviesLiveData = MutableLiveData<List<Movie>>()
    var hasError = MutableLiveData<Boolean>()


    fun searchMovie(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.searchMovie(query).await()
            withContext(Dispatchers.Main) {
                moviesLiveData.value = result.results
            }
        }
    }


    fun getHomepageMovies() {
        hasError.value = false
        CoroutineScope(Dispatchers.IO).launch {
            var result: Movies? = null
            try {
                when (sortBy) {
                    SORT_POPULAR ->
                        result = repository.getPopularMovies().await()
                    SORT_PLAYING ->
                        result = repository.getNowPlayingMovies().await()
                    SORT_UPCOMING ->
                        result = repository.getUpcomingMovies().await()
                }

                withContext(Dispatchers.Main) {
                    moviesLiveData.value = result?.results
                }
            } catch (e: Throwable) {
                Log.d("PopularViewModel", "get $sortBy movies error", e)

                withContext(Dispatchers.Main) {
                    hasError.value = true
                }
            }


        }
    }
}




