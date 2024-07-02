package com.altayiskender.movieapp.domain.usecases

import com.altayiskender.movieapp.domain.Repository
import com.altayiskender.movieapp.domain.models.MoviesResponse

class GetPopularMoviesUseCase(private val repository: Repository) {

    suspend fun invoke(page: Int): Result<MoviesResponse> {
        return repository.getPopularMovies(page)
    }
}
