package com.altayiskender.movieapp.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.utils.getBackdropUrl
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun DetailPage(
    viewModel: DetailViewModel,
    navController: NavController
) {
    val movie by viewModel.movieState
    val isLoading by viewModel.loading
    val bookmarkedStatus by viewModel.movieSavedState.collectAsState(initial = false)

    val onClickLikeButton: (Boolean) -> Unit = {
        viewModel.toggleBookmarkStatus(it)
    }

    ScaffoldWithBackground(
        movie = movie,
        isLoading = isLoading,
        bookmarkedStatus = bookmarkedStatus,
        onClickLikeButton = onClickLikeButton,
        navController = navController
    )

}

@Composable
private fun ScaffoldWithBackground(
    movie: Movie?,
    isLoading: Boolean,
    bookmarkedStatus: Boolean,
    onClickLikeButton: (Boolean) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        movie?.backdropPath?.let {
            CoilImage(
                modifier = Modifier.fillMaxSize(),
                imageModel = { getBackdropUrl(it) },
                imageOptions = ImageOptions(
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                )
            )
        }

        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
            topBar = {
                movie?.title?.let {
                    MovieDetailAppBar(
                        title = it,
                        navController = navController,
                        bookmarkedStatus = bookmarkedStatus,
                        onClickLikeButton = onClickLikeButton,
                    )
                }
            },
            content = { padding ->
                MovieDetailContent(padding, movie, navController, isLoading)
            }
        )
    }
}

@Composable
private fun MovieDetailContent(
    padding: PaddingValues,
    movie: Movie?,
    navController: NavController,
    isLoading: Boolean
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {

        movie?.let {
            MovieDetailBody(
                movie = it,
                navController = navController
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = isLoading,
                modifier = Modifier
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailAppBar(
    title: String,
    navController: NavController,
    onClickLikeButton: (Boolean) -> Unit,
    bookmarkedStatus: Boolean,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { onClickLikeButton(bookmarkedStatus) }) {
                Icon(
                    imageVector = getBookmarkIcon(bookmarkedStatus),
                    contentDescription = null
                )
            }
        }
    )
}

private fun getBookmarkIcon(bookmarked: Boolean): ImageVector {
    return if (bookmarked) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
}
