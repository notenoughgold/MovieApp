package altayiskender.movieapp.Repository

import altayiskender.movieapp.Pojos.Movie
import altayiskender.movieapp.Utils.runOnIoThread

class Repository(private val api: TmdbApi,private val movieBookmarksDao: MovieBookmarksDao) {


    // Get a list of currently popular movies.
    fun getPopularMovies() = api.getPopularMovies()

    // Get a list of upcoming movie.
    fun getUpcomingMovies() = api.getUpcomingMovies()

    // Get a list of now playing movie.
    fun getNowPlayingMovies() = api.getNowPlayingMovies()

    // Get details to the given movie.
    fun getMovieDetails(id: Long) = api.getMovieDetails(id)

    // Search all movies for the given query.
    fun searchMovie(query: String) = api.searchMovie(query)

    // Search details for the given people.
    fun getPeopleDetails(id: Long) = api.getPeopleDetails(id)

    // Get a list of bookmarked movies.
    fun getAllBookmarkedMovies() = movieBookmarksDao.loadAllBookmarkedMovies()

    // Check if movie exist in bookmarks db.
    fun checkMovieIdIfSaved(movieId:Long) = movieBookmarksDao.checkMovieIdIfSaved(movieId)

    // insert a movie into bookmarks db.
    fun insertBookmarkedMovie(bookmark: Movie) {
        runOnIoThread {
            movieBookmarksDao.insertBookmarkedMovie(bookmark)
        }
    }

    // delete a movie from bookmarks db.
    fun deleteBookmarkedMovie(bookmark: Movie) {
        runOnIoThread {
            movieBookmarksDao.deleteBookmarkedMovie(bookmark)
        }
    }
}