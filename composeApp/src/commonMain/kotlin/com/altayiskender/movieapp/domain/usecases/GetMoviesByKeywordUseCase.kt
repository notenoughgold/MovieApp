package com.altayiskender.movieapp.domain.usecases

import com.altayiskender.movieapp.domain.Repository
import com.altayiskender.movieapp.domain.models.MoviesResponse

class GetMoviesByKeywordUseCase(private val repository: Repository) {

    suspend fun invoke(keyword: String, page: Int): Result<MoviesResponse> {
        return repository.getMoviesByKeyword(keyword, page)
    }
}
