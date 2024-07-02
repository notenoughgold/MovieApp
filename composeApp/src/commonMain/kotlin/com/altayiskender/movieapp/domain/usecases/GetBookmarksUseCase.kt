package com.altayiskender.movieapp.domain.usecases

import com.altayiskender.movieapp.domain.Repository
import com.altayiskender.movieapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow

class GetBookmarksUseCase(private val repository: Repository) {
    fun invoke(): Flow<List<Movie>> {
        return repository.getAllBookmarkedMovies()
    }
}
