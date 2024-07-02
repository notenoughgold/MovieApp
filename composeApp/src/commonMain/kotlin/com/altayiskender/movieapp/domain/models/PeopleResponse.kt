package com.altayiskender.movieapp.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeopleResponse(
    val birthday: String? = null,
    val imdbId: String? = null,
    val knownForDepartment: String? = null,
    @SerialName("profile_path")
    val profilePath: String? = null,
    val biography: String? = null,
    val deathday: String? = null,
    @SerialName("place_of_birth")
    val placeOfBirth: String? = null,
    val popularity: Double? = null,
    val name: String? = null,
    @SerialName("movie_credits")
    val movieCredits: MovieCredits? = null,
    val id: Int? = null,
    val adult: Boolean? = null,
    val homepage: String? = null
)
