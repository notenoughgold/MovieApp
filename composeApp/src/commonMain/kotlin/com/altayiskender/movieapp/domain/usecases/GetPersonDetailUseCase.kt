package com.altayiskender.movieapp.domain.usecases

import com.altayiskender.movieapp.domain.Repository
import com.altayiskender.movieapp.domain.models.PeopleResponse

class GetPersonDetailUseCase(private val repository: Repository) {

    suspend fun invoke(id: Long): Result<PeopleResponse> {
        return repository.getPeopleDetails(id)
    }
}
