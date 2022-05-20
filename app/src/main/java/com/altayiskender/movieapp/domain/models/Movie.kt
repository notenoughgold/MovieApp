package com.altayiskender.movieapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "bookmarks")
data class Movie @Ignore constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @SerializedName("vote_count")
    @Ignore
    val voteCount: Int? = null,
    @SerializedName("vote_average")
    @Ignore
    val voteAverage: Double? = null,
    @Ignore
    val runtime: Int? = null,
    val title: String? = "",
    @SerializedName("release_date")
    val releaseDate: String? = "",
    @SerializedName("poster_path")
    val posterPath: String? = "",
    @SerializedName("backdrop_path")
    @Ignore
    val backdropPath: String? = null,
    @Ignore
    val genres: List<Genre>? = null,
    @Ignore
    val overview: String? = null,
    @Ignore
    val credits: Credits? = null

) : Serializable {
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