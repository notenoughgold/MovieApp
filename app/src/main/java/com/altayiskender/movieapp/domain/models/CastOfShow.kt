package com.altayiskender.movieapp.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastOfShow(
    val id: Long,
    val character: String?,
    val name: String?,
    @SerialName("profile_path")
    val profilePath: String?
)
