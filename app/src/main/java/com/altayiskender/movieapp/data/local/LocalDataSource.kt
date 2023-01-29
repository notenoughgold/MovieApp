package com.altayiskender.movieapp.data.local

import com.altayiskender.movieapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    // Get a list of bookmarked movies.
    fun getAllBookmarkedMovies(): Flow<List<Movie>>

    // Check if movie exist in bookmarks db.
    fun checkMovieIdIfSaved(movieId: Long): Flow<Boolean>

    // insert a movie into bookmarks db.
    fun insertBookmarkedMovie(bookmark: Movie)

    // delete a movie from bookmarks db.
    fun deleteBookmarkedMovie(bookmark: Movie)
}