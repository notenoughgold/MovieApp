package altayiskender.movieapp.Pojos

enum class VideoTypes {
    Trailer, Teaser, Clip, Featurette
}

data class Video(
        val key: String? = null,
        val type: String? = null,
        val site: String? = null
)