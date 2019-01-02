package altayiskender.movieapp.Repository

import altayiskender.movieapp.Pojos.Movie
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface MovieBookmarksDao {

    @Query("SELECT * FROM bookmarks ")
    fun loadAllBookmarkedMovies(): List<Movie>

    @Insert
    fun insertBookmarkedMovie(bookmark: Movie)

    @Delete
    fun deleteBookmarkedMovie(bookmark: Movie)

    @Query("SELECT id  FROM bookmarks WHERE id LIKE :movieId")
    fun checkMovieIdIfSaved(movieId:Long): List<Long>

//    @Query("SELECT * FROM bookmarks WHERE id = :id")
//    fun loadMovieBookmarkById(id: Int): Observable<Movie>
}