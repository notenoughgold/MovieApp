package com.altayiskender.movieapp.ui.popular

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.ui.common.PopularMovieItem

@Composable
fun PopularPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PopularViewModel = hiltViewModel()
) {
    val lazyMovieItems: LazyPagingItems<Movie> = viewModel.movies.collectAsLazyPagingItems()
    val isLoading by viewModel.isLoading
    val gridState = rememberLazyGridState()

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val progress = createRef()
        AnimatedVisibility(
            visible = isLoading, modifier = Modifier.constrainAs(progress) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
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



