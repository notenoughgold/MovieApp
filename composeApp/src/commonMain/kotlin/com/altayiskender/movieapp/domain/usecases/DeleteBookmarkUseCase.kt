package com.altayiskender.movieapp.domain.usecases

import com.altayiskender.movieapp.domain.Repository
import com.altayiskender.movieapp.domain.models.Movie

class DeleteBookmarkUseCase(private val repository: Repository) {
    suspend fun invoke(movie: Movie) {
        return repository.deleteBookmarkedMovie(movie)
    }
}
