package com.altayiskender.movieapp.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.altayiskender.movieapp.ui.details.DetailPage
import com.altayiskender.movieapp.ui.people.PeoplePage
import com.altayiskender.movieapp.ui.popular.PopularPage

@Composable
fun NavHostPage() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationPage.Popular.routeName) {
        composable(NavigationPage.Popular.routeName) {
            PopularPage(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable(
            route = NavigationPage.MovieDetail.routeWithArgument,
            arguments = listOf(
                navArgument(NavigationPage.MovieDetail.argument0) { type = NavType.LongType },
            )
        ) { backStackEntry ->
            val movieId =
                backStackEntry.arguments?.getLong(NavigationPage.MovieDetail.argument0)
                    ?: return@composable
            DetailPage(
                movieId = movieId,
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable(
            route = NavigationPage.PeopleDetail.routeWithArgument,
            arguments = listOf(
                navArgument(NavigationPage.PeopleDetail.argument0) { type = NavType.LongType },
            )
        ) { backStackEntry ->
            val movieId =
                backStackEntry.arguments?.getLong(NavigationPage.PeopleDetail.argument0)
                    ?: return@composable
            PeoplePage(
                peopleId = movieId,
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}

sealed class NavigationPage(val routeName: String) {

    object Popular : NavigationPage("Popular")
    object MovieDetail : NavigationPage("MovieDetail") {
        const val routeWithArgument: String = "MovieDetail/{movieId}"
        const val argument0: String = "movieId"
    }

    object PeopleDetail : NavigationPage("PeopleDetail") {
        const val routeWithArgument: String = "PeopleDetail/{peopleId}"
        const val argument0: String = "peopleId"
    }
}