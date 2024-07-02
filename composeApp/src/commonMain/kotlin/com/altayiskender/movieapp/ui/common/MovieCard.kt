package com.altayiskender.movieapp.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.ui.NavigationPage
import com.altayiskender.movieapp.utils.getPosterUrl
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun PopularMovieItem(
    movie: Movie,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        onClick = { navController.navigate("${NavigationPage.MovieDetail.routeName}/${movie.id}") }
    ) {
        CoilImage(
            modifier = Modifier.fillMaxWidth(),
            imageModel = { getPosterUrl(movie.posterPath) },
            imageOptions = ImageOptions(
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            ),
        )
    }
}
