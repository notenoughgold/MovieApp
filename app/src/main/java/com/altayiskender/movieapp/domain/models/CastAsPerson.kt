package com.altayiskender.movieapp.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastAsPerson(
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    val overview: String? = null,
    val originalLanguage: String? = null,
    val episodeCount: Int? = null,
    val genreIds: List<Int?>? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    val originCountry: List<String?>? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    val character: String,
    val creditId: String? = null,
    val mediaType: String? = null,
    val originalName: String? = null,
    val voteAverage: Double? = null,
    val popularity: Double? = null,
    val id: Long,
    val voteCount: Int? = null,
    val originalTitle: String? = null,
    val video: Boolean? = null,
    val title: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    val adult: Boolean? = null
)
