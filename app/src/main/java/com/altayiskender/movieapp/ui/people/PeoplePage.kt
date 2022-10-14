package com.altayiskender.movieapp.ui.people

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.altayiskender.movieapp.domain.models.PeopleResponse
import com.altayiskender.movieapp.ui.NavigationPage
import com.altayiskender.movieapp.utils.getPosterUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeoplePage(
    peopleId: Long,
    viewModel: PeopleViewModel,
    navController: NavController
) {
    val personState by viewModel.peopleState
    val isLoading by viewModel.isLoading

    LaunchedEffect(key1 = peopleId) {
        viewModel.getPeopleDetails(peopleId)
    }

    Scaffold(
        topBar = {
            personState?.name?.let {
                TopAppBar(
                    title = {
                        Text(
                            text = it,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }, navigationIcon = {
                        IconButton(
                            onClick = { navController.navigateUp() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    })
            }
        }, content = { padding ->
            ConstraintLayout(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                val (progress) = createRefs()

                personState?.let {
                    PersonDetailBody(
                        person = it,
                        modifier = Modifier.fillMaxSize(),
                        navController = navController
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
        }
    )
}

@Composable
fun PersonDetailBody(person: PeopleResponse, modifier: Modifier, navController: NavController) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                model = getPosterUrl(person.profilePath),
                contentScale = ContentScale.Crop,
                contentDescription = person.name,
                fallback = painterResource(id = R.drawable.ic_person_placeholder),
                error = painterResource(id = R.drawable.ic_person_placeholder),
                placeholder = painterResource(id = R.drawable.ic_person_placeholder),
                modifier = Modifier
                    .clip(CircleShape)
                    .height(150.dp)
                    .aspectRatio(1f)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
                .fillMaxWidth()
        ) {
            if (person.birthday?.isNotBlank() == true) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.Birthday),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(text = person.birthday, fontWeight = FontWeight.Bold)
                }
            }
            if (!person.placeOfBirth.isNullOrBlank()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.Birthplace),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = person.placeOfBirth.toString(),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

        }
        if (!person.movieCredits?.cast.isNullOrEmpty()) {
            Text(
                text = stringResource(id = R.string.cast),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(person.movieCredits!!.cast!!.size) {
                    val movie = person.movieCredits.cast!![it]
                    val name = movie!!.title
                    val id = movie.id
                    val url = movie.posterPath
                    val work = movie.character
                    val date = movie.releaseDate
                    CreditItem(id, name, url, work, date, navController)
                }
            }
        }

        if (!person.movieCredits?.crew.isNullOrEmpty()) {
            Text(
                text = stringResource(id = R.string.crew),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(person.movieCredits!!.crew!!.size) {
                    val movie = person.movieCredits.crew!![it]
                    val name = movie!!.title
                    val id = movie.id
                    val url = movie.posterPath
                    val work = movie.job
                    val date = movie.releaseDate
                    CreditItem(id, name, url, work, date, navController)
                }
            }
        }
        if (!person.biography.isNullOrBlank()) {
            Text(
                text = person.biography,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreditItem(
    id: Long,
    name: String?,
    url: String?,
    work: String?,
    date: String?,
    navController: NavController
) {
    Surface(onClick = { onClickItem(id, navController) }, color = Color.Transparent) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(100.dp)
        ) {
            AsyncImage(
                model = getPosterUrl(url),
                modifier = Modifier.aspectRatio(0.67f),
                fallback = painterResource(id = R.drawable.ic_person_placeholder),
                error = painterResource(id = R.drawable.ic_person_placeholder),
                placeholder = painterResource(id = R.drawable.ic_person_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            if (!name.isNullOrBlank()) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (!work.isNullOrBlank()) {
                Text(
                    text = work,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (!date.isNullOrBlank()) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun onClickItem(id: Long, navController: NavController) {
    navController.navigate("${NavigationPage.MovieDetail.routeName}/${id}")
}