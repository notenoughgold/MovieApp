package com.altayiskender.movieapp.domain.models

import com.google.gson.annotations.SerializedName

data class PeopleResponse(
    val birthday: String? = null,
    val imdbId: String? = null,
    val knownForDepartment: String? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null,
    val biography: String? = null,
    val deathday: Any? = null,
    @SerializedName("place_of_birth")
    val placeOfBirth: String? = null,
    val popularity: Double? = null,
    val name: String? = null,
    @SerializedName("movie_credits")
    val movieCredits: MovieCredits? = null,
    val id: Int? = null,
    val adult: Boolean? = null,
    val homepage: String? = null
)
