package altayiskender.movieapp.Repository

import altayiskender.movieapp.Pojos.Movie
import altayiskender.movieapp.Utils.runOnIoThread

class Repository(private val api: TmdbApi,private val movieBookmarksDao: MovieBookmarksDao) {

    /**
     * Get a list of currently popular movies.
     */
    suspend fun getPopularMovies() = api.getPopularMovies()

    /**
     * Get a list of upcoming movie.
     */
    suspend fun getUpcomingMovies() = api.getUpcomingMovies()

    /**
     * Get a list of now playing movie.
     */
    suspend fun getNowPlayingMovies() = api.getNowPlayingMovies()

    /**
     * Get details to the given movie.
     */
    suspend fun getMovieDetails(id: Long) = api.getMovieDetails(id)

    /**
     * Search all movies for the given query.
     */
    suspend fun searchMovie(query: String) = api.searchMovie(query)

    /**
     * Search details for the given people.
     */
    suspend fun getPeopleDetails(id: Long) = api.getPeopleDetails(id)

    suspend fun getAllBookmarkedMovies() = movieBookmarksDao.loadAllBookmarkedMovies()

    suspend fun checkMovieIdIfSaved( movieId:Long) = movieBookmarksDao.checkMovieIdIfSaved(movieId)

    fun insertBookmarkedMovie(bookmark: Movie) {
        runOnIoThread {
            movieBookmarksDao.insertBookmarkedMovie(bookmark)
        }
    }

    fun deleteBookmarkedMovie(bookmark: Movie) {
        runOnIoThread {
            movieBookmarksDao.deleteBookmarkedMovie(bookmark)
        }
    }
}