package altayiskender.movieapp.ui.bookmarks

import altayiskender.movieapp.models.Movie
import altayiskender.movieapp.repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarksViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var bookmarksLiveData = MutableLiveData<List<Movie>>()


    fun getAllBookmarkedMovies(): MutableLiveData<List<Movie>>? {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getAllBookmarkedMovies()
            withContext(Dispatchers.Main) {
                bookmarksLiveData.value = result
            }
        }

        return bookmarksLiveData
    }

}