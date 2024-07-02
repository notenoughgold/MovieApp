package com.altayiskender.movieapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.altayiskender.movieapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieBookmarksDao {

    @Query("SELECT * FROM bookmarks")
    fun loadAllBookmarkedMovies(): Flow<List<Movie>>

    @Insert
    suspend fun insertBookmarkedMovie(bookmark: Movie)

    @Delete
    suspend fun deleteBookmarkedMovie(bookmark: Movie)

    @Query("SELECT EXISTS(SELECT * FROM bookmarks WHERE id = :id)")
    fun checkMovieIdIfSaved(id: Long): Flow<Boolean>

}
