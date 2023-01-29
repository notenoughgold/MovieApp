package com.altayiskender.movieapp.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.altayiskender.movieapp.R
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.ui.NavigationPage
import com.altayiskender.movieapp.utils.getBackdropUrl
import com.altayiskender.movieapp.utils.getPosterUrl

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
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
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

@Composable
private fun MovieDetailBody(movie: Movie, modifier: Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
                .fillMaxWidth()
        ) {
            if (movie.releaseDate?.isNotBlank() == true) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.release_date),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(text = movie.releaseDate, fontWeight = FontWeight.Bold)
                }
            }
            if (movie.voteAverage != null && movie.voteAverage > 0) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.rating),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(text = movie.voteAverage.toString(), fontWeight = FontWeight.Bold)
                }
            }
            if (movie.runtime != null && movie.runtime > 0) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.runtime),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(text = movie.runtime.toString(), fontWeight = FontWeight.Bold)
                }
            }
        }
        if (!movie.genres.isNullOrEmpty()) {
            val genres = movie.genres
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(genres.size) {
                    GenreChip(text = genres[it].name)
                }
            }
        }
        movie.overview?.let {
            Text(
                text = it,
                modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }


        if (!movie.credits?.cast.isNullOrEmpty()) {
            Text(
                text = stringResource(id = R.string.cast),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movie.credits!!.cast!!.size) {
                    val person = movie.credits.cast!![it]
                    val name = person.name
                    val id = person.id
                    val url = person.profilePath
                    val work = person.character
                    PersonItem(id, name, url, work, navController)
                }
            }
        }

        if (!movie.credits?.crew.isNullOrEmpty()) {
            Text(
                text = stringResource(id = R.string.crew),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movie.credits!!.crew!!.size) {
                    val person = movie.credits.crew!![it]
                    val name = person.name
                    val id = person.id
                    val url = person.profilePath
                    val work = person.job
                    PersonItem(id, name, url, work, navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PersonItem(
    id: Long,
    name: String,
    url: String?,
    work: String,
    navController: NavController
) {
    Surface(onClick = { onClickItem(id, navController) }, color = Color.Transparent) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(100.dp)
        ) {
            AsyncImage(
                model = getPosterUrl(url),
                fallback = painterResource(id = R.drawable.ic_person_placeholder),
                error = painterResource(id = R.drawable.ic_person_placeholder),
                placeholder = painterResource(id = R.drawable.ic_person_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .height(80.dp)
                    .aspectRatio(1f)
            )
            Text(
                text = name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = work,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun GenreChip(text: String) {
    Box(
        modifier = Modifier.border(
            1.dp,
            color = MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(corner = CornerSize(12.dp))
        )
    ) {
        Text(text = text, Modifier.padding(8.dp))
    }
}

private fun onClickItem(id: Long, navController: NavController) {
    navController.navigate("${NavigationPage.PeopleDetail.routeName}/${id}")
}

private fun getBookmarkIcon(bookmarked: Boolean): ImageVector {
    return if (bookmarked) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
}
