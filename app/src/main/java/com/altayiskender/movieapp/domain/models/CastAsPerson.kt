package com.altayiskender.movieapp.domain.models

import com.google.gson.annotations.SerializedName

data class CastAsPerson(
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    val overview: String? = null,
    val originalLanguage: String? = null,
    val episodeCount: Int? = null,
    val genreIds: List<Int?>? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    val originCountry: List<String?>? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    val character: String? = null,
    val creditId: String? = null,
    val mediaType: String? = null,
    val originalName: String? = null,
    val voteAverage: Double? = null,
    val popularity: Double? = null,
    val name: String? = null,
    val id: Long? = null,
    val voteCount: Int? = null,
    val originalTitle: String? = null,
    val video: Boolean? = null,
    val title: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val adult: Boolean? = null
)
