package com.altayiskender.movieapp.domain.usecases

import com.altayiskender.movieapp.domain.Repository
import kotlinx.coroutines.flow.Flow

class GetBookmarkStatusUseCase(private val repository: Repository) {
    fun invoke(id: Long): Flow<Boolean> {
        return repository.checkMovieIdIfSaved(id)
    }
}
