package com.altayiskender.movieapp.ui.people

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
    viewModel: PeopleViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val personState by viewModel.peopleState
    val isLoading by viewModel.isLoading

    Scaffold(
        modifier = modifier,
        topBar = {
            personState?.name?.let {
                TopAppBar(
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                val progress = createRef()

                personState?.let {
                    PersonDetailBody(
                        person = it,
                        navController = navController
                    )
                }
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
            }
        }
    )
}

@Composable
fun PersonDetailBody(
    person: PeopleResponse,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
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

        PersonInfoRow(person)

        if (!person.movieCredits?.cast.isNullOrEmpty()) {
            Text(
                text = stringResource(id = R.string.cast),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            CreditsLazyRow(
                person.movieCredits?.cast.orEmpty().map {
                    CreditUiModel.createFrom(it)
                },
                navController
            )
        }

        if (!person.movieCredits?.crew.isNullOrEmpty()) {
            Text(
                text = stringResource(id = R.string.crew),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            CreditsLazyRow(
                person.movieCredits?.crew.orEmpty().map {
                    CreditUiModel.createFrom(it)
                },
                navController
            )
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

@Composable
private fun PersonInfoRow(
    person: PeopleResponse,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
            .fillMaxWidth()
    ) {
        if (!person.birthday.isNullOrBlank()) {
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
                    text = person.placeOfBirth,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun CreditsLazyRow(
    credits: List<CreditUiModel>,
    navController: NavController
) {
    LazyRow(
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(credits.size) {
            CreditItem(credits[it], navController)
        }
    }
}

@Composable
private fun CreditItem(
    credit: CreditUiModel,
    navController: NavController
) {
    Surface(
        onClick = { onClickItem(credit.id, navController) },
        color = Color.Transparent
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(100.dp)
        ) {
            AsyncImage(
                model = getPosterUrl(credit.url),
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .aspectRatio(ratio = 0.67f)
                    .padding(bottom = 4.dp),
                fallback = painterResource(id = R.drawable.ic_person_placeholder),
                error = painterResource(id = R.drawable.ic_person_placeholder),
                placeholder = painterResource(id = R.drawable.ic_person_placeholder),
                contentDescription = credit.name,
                contentScale = ContentScale.Crop,
            )
            if (!credit.name.isNullOrBlank()) {
                Text(
                    text = credit.name,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (!credit.work.isNullOrBlank()) {
                Text(
                    text = credit.work,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (!credit.date.isNullOrBlank()) {
                Text(
                    text = credit.date,
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
