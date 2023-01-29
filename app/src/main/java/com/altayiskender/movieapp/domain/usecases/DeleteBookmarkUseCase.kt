package com.altayiskender.movieapp.domain.usecases

import com.altayiskender.movieapp.domain.Repository
import com.altayiskender.movieapp.domain.models.Movie
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(private val repository: Repository) {
    fun invoke(movie: Movie) {
        return repository.deleteBookmarkedMovie(movie)
    }
}
