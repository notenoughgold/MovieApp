package com.altayiskender.movieapp.data.local

import com.altayiskender.movieapp.domain.models.Movie

class LocalDataSourceImpl(private val dao: MovieBookmarksDao) : LocalDataSource {

    override fun getAllBookmarkedMovies() = dao.loadAllBookmarkedMovies()

    override fun checkMovieIdIfSaved(movieId: Long) = dao.checkMovieIdIfSaved(movieId)

    override suspend fun insertBookmarkedMovie(bookmark: Movie) = dao.insertBookmarkedMovie(bookmark)

    override suspend fun deleteBookmarkedMovie(bookmark: Movie) = dao.deleteBookmarkedMovie(bookmark)
}
