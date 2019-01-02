package altayiskender.movieapp.Pojos

data class VideosPage(
        val results: List<Video>? = null
) {

    fun getYoutubeTrailer(): Video? {
        results?.forEach {video ->
            if (video.type.equals(VideoTypes.Trailer.name) && video.site.equals("YouTube")) {
                return video
            }
        }
        return null
    }

}