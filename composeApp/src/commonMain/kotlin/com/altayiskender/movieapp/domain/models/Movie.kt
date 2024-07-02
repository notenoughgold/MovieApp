package com.altayiskender.movieapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "bookmarks")
@Serializable
data class Movie @Ignore constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @SerialName("vote_count")
    @Ignore
    val voteCount: Int? = null,
    @SerialName("vote_average")
    @Ignore
    val voteAverage: Double? = null,
    @Ignore
    val runtime: Int? = null,
    val title: String? = "",
    @SerialName("release_date")
    val releaseDate: String? = "",
    @SerialName("poster_path")
    val posterPath: String? = "",
    @SerialName("backdrop_path")
    @Ignore
    val backdropPath: String? = null,
    @Ignore
    val genres: List<Genre>? = null,
    @Ignore
    val overview: String? = null,
    @Ignore
    val credits: Credits? = null
) {

    constructor(id: Long, title: String?, releaseDate: String?, posterPath: String?) : this(
        id,
        0,
        0.0,
        0,
        title,
        releaseDate,
        posterPath,
        null,
        null,
        null,
        null
    )

}
