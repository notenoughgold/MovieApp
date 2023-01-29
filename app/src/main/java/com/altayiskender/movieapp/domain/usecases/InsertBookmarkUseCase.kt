package com.altayiskender.movieapp.domain.usecases

import com.altayiskender.movieapp.domain.Repository
import com.altayiskender.movieapp.domain.models.Movie
import javax.inject.Inject

class InsertBookmarkUseCase @Inject constructor(private val repository: Repository) {
    fun invoke(movie: Movie) {
        return repository.insertBookmarkedMovie(movie)
    }
}
