package altayiskender.movieapp.Repository

import altayiskender.movieapp.Pojos.Movie
import altayiskender.movieapp.Pojos.Movies
import altayiskender.movieapp.Pojos.PeopleResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    fun getPopularMovies(): Deferred<Movies>

    @GET("movie/upcoming")
    fun getUpcomingMovies(): Deferred<Movies>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(): Deferred<Movies>

    @GET("movie/{movie_id}?append_to_response=credits,videos")
    fun getMovieDetails(@Path(value = "movie_id")movieId: Long): Deferred<Movie>

    @GET("search/movie")
    fun searchMovie(@Query("query") query: String): Deferred<Movies>

    @GET("person/{person_id}?append_to_response=combined_credits")
    fun getPeopleDetails(@Path(value = "person_id")personId:Long):Deferred<PeopleResponse>
}