package com.altayiskender.movieapp.domain.models

import com.google.gson.annotations.SerializedName

data class CrewAsPerson(
    val firstAirDate: String? = null,
    val overview: String? = null,
    val originalLanguage: String? = null,
    val episodeCount: Int? = null,
    val genreIds: List<Int?>? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    val originCountry: List<String?>? = null,
    val backdropPath: String? = null,
    val creditId: String? = null,
    val mediaType: String? = null,
    val originalName: String? = null,
    val popularity: Double? = null,
    val voteAverage: Double? = null,
    val id: Long,
    val department: String? = null,
    val job: String,
    val voteCount: Int? = null,
    val originalTitle: String? = null,
    val video: Boolean? = null,
    val title: String,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val adult: Boolean? = null
)
