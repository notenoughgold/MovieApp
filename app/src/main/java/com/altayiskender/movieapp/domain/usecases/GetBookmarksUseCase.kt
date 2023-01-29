package com.altayiskender.movieapp.domain.usecases

import com.altayiskender.movieapp.domain.Repository
import com.altayiskender.movieapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarksUseCase @Inject constructor(private val repository: Repository) {
    fun invoke(): Flow<List<Movie>> {
        return repository.getAllBookmarkedMovies()
    }
}
