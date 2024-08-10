package com.altayiskender.movieapp.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.ui.NavigationRoute
import com.altayiskender.movieapp.utils.getPosterUrl

@Composable
fun PopularMovieItem(
    movie: Movie,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        onClick = { navController.navigate("${NavigationRoute.ParametricRoute.MovieDetail.routeRoot}/${movie.id}") }
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = getPosterUrl(movie.posterPath),
            contentDescription = movie.title,
            contentScale = ContentScale.FillWidth
        )
    }
}
