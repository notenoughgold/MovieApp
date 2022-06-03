package com.altayiskender.movieapp.data

import com.altayiskender.movieapp.data.local.LocalDataSource
import com.altayiskender.movieapp.data.remote.RemoteDataSource
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.models.MoviesResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {


    // Get a list of currently popular movies.
    override suspend fun getPopularMovies(page: Int): Result<MoviesResponse> =
        getResult { remoteDataSource.getPopularMovies(page) }

    // Get a list of upcoming movie.
    override suspend fun getUpcomingMovies() = remoteDataSource.getUpcomingMovies()

    // Get a list of now playing movie.
    override suspend fun getNowPlayingMovies() = remoteDataSource.getNowPlayingMovies()

    // Get details to the given movie.
    override suspend fun getMovieDetails(id: Long): Result<Movie> =
        getResult { remoteDataSource.getMovieDetails(id) }

    // Search all movies for the given query.
    override suspend fun searchMovie(query: String) = remoteDataSource.searchMovie(query)

    // Search details for the given people.
    override suspend fun getPeopleDetails(id: Long) =
        getResult { remoteDataSource.getPeopleDetails(id) }

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
