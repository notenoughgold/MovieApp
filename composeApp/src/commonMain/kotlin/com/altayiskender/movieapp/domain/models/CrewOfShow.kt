package com.altayiskender.movieapp.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrewOfShow(
    val id: Long,
    val job: String?,
    val name: String?,
    @SerialName("profile_path")
    val profilePath: String?
)
