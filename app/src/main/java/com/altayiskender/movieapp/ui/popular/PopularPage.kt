package com.altayiskender.movieapp.ui.popular

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.altayiskender.movieapp.domain.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularPage(viewModel: PopularViewModel, navController: NavController) {

    val lazyMovieItems: LazyPagingItems<Movie> = viewModel.movies.collectAsLazyPagingItems()
    val hasError by viewModel.hasError
    val isLoading by viewModel.isLoading
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()


    Scaffold(topBar = { PosterAppBar(gridState, coroutineScope) }, content = {
        ConstraintLayout(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            val (progress) = createRefs()
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
                        val movie = lazyMovieItems[index]
                        if (movie != null) {
                            PopularMovieItem(movie = movie, navController = navController)
                        }
                    })
            }
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PosterAppBar(gridState: LazyGridState, coroutineScope: CoroutineScope) {
    TopAppBar(title = {
        Text(text = "Popular Movies")
    }, navigationIcon = {
        IconButton(onClick = { scrollToTop(gridState, coroutineScope) }) {
            Icon(imageVector = Icons.Default.Star, contentDescription = null)
        }
    })
}

private fun scrollToTop(gridState: LazyGridState, coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        gridState.animateScrollToItem(0)
    }
}



