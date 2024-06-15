package com.altayiskender.movieapp.data

import com.altayiskender.movieapp.data.local.LocalDataSource
import com.altayiskender.movieapp.data.remote.RemoteDataSource
import com.altayiskender.movieapp.domain.Repository
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.models.MoviesResponse

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {

    // Get a list of currently popular movies.
    override suspend fun getPopularMovies(page: Int): Result<MoviesResponse> =
        runCatching { remoteDataSource.getPopularMovies(page) }

    // Get a list of upcoming movie.
    override suspend fun getUpcomingMovies(): Result<MoviesResponse> =
        runCatching { remoteDataSource.getUpcomingMovies() }

    // Get a list of now playing movie.
    override suspend fun getNowPlayingMovies(): Result<MoviesResponse> =
        runCatching { remoteDataSource.getNowPlayingMovies() }

    // Get details to the given movie.
    override suspend fun getMovieDetails(id: Long): Result<Movie> =
        runCatching { remoteDataSource.getMovieDetails(id) }

    // Search all movies for the given query.
    override suspend fun searchMovie(query: String) = remoteDataSource.searchMovie(query)

    // Search details for the given people.
    override suspend fun getPeopleDetails(id: Long) =
        runCatching { remoteDataSource.getPeopleDetails(id) }

    // Get a list of bookmarked movies.
    override fun getAllBookmarkedMovies() = localDataSource.getAllBookmarkedMovies()

    // Check if movie exist in bookmarks db.
    override fun checkMovieIdIfSaved(movieId: Long) = localDataSource.checkMovieIdIfSaved(movieId)

    // insert a movie into bookmarks db.
    override fun insertBookmarkedMovie(bookmark: Movie) =
        localDataSource.insertBookmarkedMovie(bookmark)

    // delete a movie from bookmarks db.
    override fun deleteBookmarkedMovie(bookmark: Movie) =
        localDataSource.deleteBookmarkedMovie(bookmark)
}
