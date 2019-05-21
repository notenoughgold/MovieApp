package altayiskender.movieapp.models



data class CombinedCredits(
	val cast: List<CastAsPerson?>? = null,
	val crew: List<CrewAsPerson?>? = null
)
