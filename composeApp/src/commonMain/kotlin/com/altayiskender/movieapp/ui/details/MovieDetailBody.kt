package com.altayiskender.movieapp.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.altayiskender.movieapp.domain.models.Genre
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.ui.NavigationRoute
import movieapp.composeapp.generated.resources.Res
import movieapp.composeapp.generated.resources.cast
import movieapp.composeapp.generated.resources.crew
import movieapp.composeapp.generated.resources.rating
import movieapp.composeapp.generated.resources.release_date
import movieapp.composeapp.generated.resources.runtime
import org.jetbrains.compose.resources.stringResource

@Composable
fun MovieDetailBody(
    movie: Movie,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        MovieInfoRow(movie)

        if (!movie.genres.isNullOrEmpty()) {
            GenreChips(movie.genres)
        }

        movie.overview?.let {
            MovieSynopsis(it)
        }

        if (movie.credits?.cast.isNullOrEmpty().not()) {
            Text(
                text = stringResource(resource = Res.string.cast),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            PersonnelLazyRow(
                movie.credits?.cast.orEmpty().map {
                    PersonUiModel.createFrom(it)
                },
                navController
            )
        }

        if (!movie.credits?.crew.isNullOrEmpty()) {
            Text(
                text = stringResource(resource = Res.string.crew),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            PersonnelLazyRow(
                movie.credits?.crew.orEmpty().map {
                    PersonUiModel.createFrom(it)
                },
                navController
            )
        }
    }
}

@Composable
private fun PersonnelLazyRow(
    personnel: List<PersonUiModel>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    fun onClickPerson(id: Long) {
        navController.navigate("${NavigationRoute.PeopleDetail.routeName}/${id}")
    }

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(personnel.size) {
            PersonItem(
                person = personnel[it],
                onClick = { onClickPerson(personnel[it].id) }
            )
        }
    }

}

@Composable
private fun MovieSynopsis(it: String, modifier: Modifier = Modifier) {
    Text(
        text = it,
        modifier = modifier.padding(16.dp, 0.dp, 16.dp, 16.dp),
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
private fun GenreChips(
    genres: List<Genre>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres.size) {
            GenreChip(text = genres[it].name)
        }
    }
}

@Composable
private fun MovieInfoRow(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
            .fillMaxWidth()
    ) {
        if (movie.releaseDate?.isNotBlank() == true) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(resource = Res.string.release_date),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = movie.releaseDate, fontWeight = FontWeight.Bold)
            }
        }
        if (movie.voteAverage != null && movie.voteAverage > 0) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(resource = Res.string.rating),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = movie.voteAverage.toString(), fontWeight = FontWeight.Bold)
            }
        }
        if (movie.runtime != null && movie.runtime > 0) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(resource = Res.string.runtime),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = movie.runtime.toString(), fontWeight = FontWeight.Bold)
            }
        }
    }
}
