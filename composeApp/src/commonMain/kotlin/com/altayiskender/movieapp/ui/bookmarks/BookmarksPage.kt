package com.altayiskender.movieapp.ui.bookmarks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.altayiskender.movieapp.ui.common.PopularMovieItem

@Composable
fun BookmarksPage(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: BookmarksViewModel
) {
    val bookmarkedMovieItems by viewModel.getAllBookmarkedMovies()
        .collectAsState(initial = emptyList())

    if (bookmarkedMovieItems.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No bookmarks")
        }
    } else {
        LazyVerticalGrid(
            modifier = modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            content = {
                items(
                    count = bookmarkedMovieItems.size,
                    itemContent = { index: Int ->
                        PopularMovieItem(
                            movie = bookmarkedMovieItems[index],
                            navController = navController
                        )
                    }
                )
            }
        )
    }

}
