package com.altayiskender.movieapp.data.local

import com.altayiskender.movieapp.domain.models.Movie

interface LocalDataSource {

    // Get a list of bookmarked movies.
    fun getAllBookmarkedMovies(): List<Movie>

    // Check if movie exist in bookmarks db.
    fun checkMovieIdIfSaved(movieId: Long): List<Long>

    // insert a movie into bookmarks db.
    fun insertBookmarkedMovie(bookmark: Movie)

    // delete a movie from bookmarks db.
    fun deleteBookmarkedMovie(bookmark: Movie)
}