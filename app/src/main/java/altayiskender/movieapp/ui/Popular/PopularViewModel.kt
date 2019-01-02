package altayiskender.movieapp.ui.Popular

import altayiskender.movieapp.Pojos.Movie
import altayiskender.movieapp.Repository.Repository
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


    fun searchMovie(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.searchMovie(query).await()
            withContext(Dispatchers.Main) {
                moviesLiveData.value = result.results
            }
        }
    }


    fun getHomepageMovies() {
        when (sortBy) {
            SORT_POPULAR -> CoroutineScope(Dispatchers.IO).launch {
                val result = repository.getPopularMovies().await()

                withContext(Dispatchers.Main) {
                    moviesLiveData.value = result.results
                }
            }

            SORT_PLAYING ->
                CoroutineScope(Dispatchers.IO).launch {
                    val result = repository.getNowPlayingMovies().await()

                    withContext(Dispatchers.Main) {
                        moviesLiveData.value = result.results
                    }
                }

            SORT_UPCOMING ->
                CoroutineScope(Dispatchers.IO).launch {
                    val result = repository.getUpcomingMovies().await()

                    withContext(Dispatchers.Main) {
                        moviesLiveData.value = result.results
                    }

                }
        }
    }
}



