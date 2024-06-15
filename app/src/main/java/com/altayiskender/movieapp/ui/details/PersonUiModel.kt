package com.altayiskender.movieapp.ui.details

import com.altayiskender.movieapp.domain.models.CastOfShow
import com.altayiskender.movieapp.domain.models.CrewOfShow

data class PersonUiModel(
    val id: Long,
    val role: String,
    val name: String,
    val profilePath: String
) {
    companion object {
        fun createFrom(crewOfShow: CrewOfShow): PersonUiModel {
            return PersonUiModel(
                id = crewOfShow.id,
                role = crewOfShow.job.orEmpty(),
                name = crewOfShow.name.orEmpty(),
                profilePath = crewOfShow.profilePath.orEmpty()
            )
        }

        fun createFrom(castOfShow: CastOfShow): PersonUiModel {
            return PersonUiModel(
                id = castOfShow.id,
                role = castOfShow.character.orEmpty(),
                name = castOfShow.name.orEmpty(),
                profilePath = castOfShow.profilePath.orEmpty()
            )
        }

    }
}
