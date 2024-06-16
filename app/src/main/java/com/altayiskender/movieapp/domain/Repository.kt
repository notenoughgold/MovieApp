package com.altayiskender.movieapp.domain

import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.models.MoviesResponse
import com.altayiskender.movieapp.domain.models.PeopleResponse
import kotlinx.coroutines.flow.Flow

interface Repository {

    // Get a list of currently popular movies.
    suspend fun getPopularMovies(page: Int): Result<MoviesResponse>

    // Get details to the given movie.
    suspend fun getMovieDetails(id: Long): Result<Movie>

    // Search details for the given people.
    suspend fun getPeopleDetails(id: Long): Result<PeopleResponse>

    // Get a list of bookmarked movies.
    fun getAllBookmarkedMovies(): Flow<List<Movie>>

    // Check if movie exist in bookmarks db.
    fun checkMovieIdIfSaved(movieId: Long): Flow<Boolean>

    // insert a movie into bookmarks db.
    fun insertBookmarkedMovie(bookmark: Movie)

    // delete a movie from bookmarks db.
    fun deleteBookmarkedMovie(bookmark: Movie)
}
