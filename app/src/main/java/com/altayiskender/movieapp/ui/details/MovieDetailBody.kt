package com.altayiskender.movieapp.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.altayiskender.movieapp.R
import com.altayiskender.movieapp.domain.models.Movie

@Composable
fun MovieDetailBody(movie: Movie, modifier: Modifier, navController: NavController) {
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
