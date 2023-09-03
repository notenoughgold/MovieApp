package com.altayiskender.movieapp.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.altayiskender.movieapp.utils.getBackdropUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(
    viewModel: DetailViewModel,
    navController: NavController
) {
    val movie by viewModel.movieState
//    val hasError by viewModel.hasError
    val isLoading by viewModel.loading
    val bookmarkedStatus by viewModel.movieSavedState.collectAsState(initial = false)

    Box {
        movie?.backdropPath?.let {
            AsyncImage(
                model = getBackdropUrl(it),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight,
            )
        }
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
        topBar = {
            movie?.title?.let {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    title = {
                        Text(
                            text = it,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.navigateUp() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.toggleBookmarkStatus(bookmarkedStatus) }) {
                            Icon(
                                imageVector = getBookmarkIcon(bookmarkedStatus),
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }, content = { padding ->
            ConstraintLayout(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                val (progress) = createRefs()

                movie?.let {
                    MovieDetailBody(
                        movie = it, modifier = Modifier.fillMaxSize(), navController = navController
                    )
                }
                AnimatedVisibility(visible = isLoading, modifier = Modifier.constrainAs(progress) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                    CircularProgressIndicator()
                }
            }
        })
}

private fun getBookmarkIcon(bookmarked: Boolean): ImageVector {
    return if (bookmarked) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
}
