package com.altayiskender.movieapp.ui.popular

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.ui.common.PopularMovieItem

@Composable
fun PopularPage(
    viewModel: PopularViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val lazyMovieItems: LazyPagingItems<Movie> = viewModel.movies.collectAsLazyPagingItems()
    val isLoading by viewModel.isLoading
    val gridState = rememberLazyGridState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isLoading
        ) {
            CircularProgressIndicator()
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            state = gridState
        ) {
            items(
                count = lazyMovieItems.itemCount,
                itemContent = { index: Int ->
                    lazyMovieItems[index]?.let {
                        PopularMovieItem(
                            movie = it,
                            navController = navController
                        )
                    }
                }
            )
        }
    }
}
