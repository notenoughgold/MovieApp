package com.altayiskender.movieapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.altayiskender.movieapp.ui.details.DetailPage
import com.altayiskender.movieapp.ui.details.DetailViewModel
import com.altayiskender.movieapp.ui.people.PeoplePage
import com.altayiskender.movieapp.ui.people.PeopleViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavHostPage() {
    val navController: NavHostController = rememberNavController()
    var bottomNavigationIndex by rememberSaveable { mutableIntStateOf(0) }

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            BottomNavigationPage(
                navController = navController,
                bottomNavigationIndex = bottomNavigationIndex,
                onBottomNavigation = { bottomNavigationIndex = it }
            )
        }
        composable<MovieDetailRoute> {
            val movieId: Long = it.toRoute<MovieDetailRoute>().movieId
            DetailPage(
                viewModel = koinViewModel<DetailViewModel>(
                    parameters = { parametersOf(movieId) }
                ),
                navController = navController
            )
        }
        composable<PeopleDetailRoute> {
            val personId = it.toRoute<PeopleDetailRoute>().personId
            PeoplePage(
                viewModel = koinViewModel<PeopleViewModel>(
                    parameters = { parametersOf(personId) }
                ),
                navController = navController
            )
        }
    }
}

sealed interface HomeBottomNavigationRoute {
    @Serializable
    data object Popular : HomeBottomNavigationRoute

    @Serializable
    data object Search : HomeBottomNavigationRoute

    @Serializable
    data object Bookmarks : HomeBottomNavigationRoute
}

@Serializable
data object HomeRoute

@Serializable
data class MovieDetailRoute(val movieId: Long)

@Serializable
data class PeopleDetailRoute(val personId: Long)
