package com.altayiskender.movieapp.ui.people

import com.altayiskender.movieapp.domain.models.CastAsPerson
import com.altayiskender.movieapp.domain.models.CrewAsPerson

data class CreditUiModel(
    val id: Long,
    val name: String?,
    val url: String?,
    val work: String?,
    val date: String?,
) {
    companion object {
        fun createFrom(credit: CrewAsPerson): CreditUiModel {
            return CreditUiModel(
                credit.id,
                credit.title,
                credit.posterPath,
                credit.job,
                credit.releaseDate
            )
        }

        fun createFrom(credit: CastAsPerson): CreditUiModel {
            return CreditUiModel(
                credit.id,
                credit.title,
                credit.posterPath,
                credit.character,
                credit.releaseDate
            )
        }
    }
}
