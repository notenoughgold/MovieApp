package com.altayiskender.movieapp.domain.usecases

import com.altayiskender.movieapp.data.Repository
import com.altayiskender.movieapp.data.Result
import com.altayiskender.movieapp.domain.models.MoviesResponse
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun invoke(page: Int): Result<MoviesResponse> {
        return repository.getPopularMovies(page)
    }
}