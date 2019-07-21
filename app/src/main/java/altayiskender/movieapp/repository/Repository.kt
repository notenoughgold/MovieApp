package altayiskender.movieapp.repository

import altayiskender.movieapp.models.Movie

class Repository(private val api: TmdbApi,private val movieBookmarksDao: MovieBookmarksDao) {


    // Get a list of currently popular movies.
    suspend fun getPopularMovies() = api.getPopularMovies()

    // Get a list of upcoming movie.
    suspend fun getUpcomingMovies() = api.getUpcomingMovies()

    // Get a list of now playing movie.
    suspend fun getNowPlayingMovies() = api.getNowPlayingMovies()

    // Get details to the given movie.
    suspend fun getMovieDetails(id: Long) = api.getMovieDetails(id)

    // Search all movies for the given query.
    suspend fun searchMovie(query: String) = api.searchMovie(query)

    // Search details for the given people.
    suspend fun getPeopleDetails(id: Long) = api.getPeopleDetails(id)

    // Get a list of bookmarked movies.
    fun getAllBookmarkedMovies() = movieBookmarksDao.loadAllBookmarkedMovies()

    // Check if movie exist in bookmarks db.
    fun checkMovieIdIfSaved(movieId:Long) = movieBookmarksDao.checkMovieIdIfSaved(movieId)

    // insert a movie into bookmarks db.
    fun insertBookmarkedMovie(bookmark: Movie) {
            movieBookmarksDao.insertBookmarkedMovie(bookmark)

    }

    // delete a movie from bookmarks db.
    fun deleteBookmarkedMovie(bookmark: Movie) {
            movieBookmarksDao.deleteBookmarkedMovie(bookmark)

    }
}