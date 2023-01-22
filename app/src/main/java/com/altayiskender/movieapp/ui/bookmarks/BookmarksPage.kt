package com.altayiskender.movieapp.ui.bookmarks

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.ui.popular.PopularMovieItem

@Composable
fun BookmarksPage(vm: BookmarksViewModel = hiltViewModel(), navController: NavHostController) {

    val bookmarkedMovieItems = listOf<Movie>()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(
                count = bookmarkedMovieItems.size,
                itemContent = { index: Int ->
                    val movie = bookmarkedMovieItems[index]
                    if (movie != null) {
                        PopularMovieItem(
                            movie = movie,
                            navController = navController
                        )
                    }
                }
            )
        }
    )
}
