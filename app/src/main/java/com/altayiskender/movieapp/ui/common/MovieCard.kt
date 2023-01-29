package com.altayiskender.movieapp.ui.popular

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.ui.NavigationPage
import com.altayiskender.movieapp.utils.getPosterUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularMovieItem(movie: Movie, navController: NavController) {
    Surface(onClick = { navController.navigate("${NavigationPage.MovieDetail.routeName}/${movie.id}") }) {
        Column {
            AsyncImage(
                model = getPosterUrl(movie.posterPath),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}
