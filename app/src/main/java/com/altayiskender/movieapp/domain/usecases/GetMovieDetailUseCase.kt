package com.altayiskender.movieapp.domain.usecases

import com.altayiskender.movieapp.domain.Repository
import com.altayiskender.movieapp.domain.models.Movie
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val repository: Repository) {
    suspend fun invoke(id: Long): Result<Movie> {
        return repository.getMovieDetails(id)
    }
}