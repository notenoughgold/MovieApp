package com.altayiskender.movieapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.altayiskender.movieapp.ui.details.DetailPage
import com.altayiskender.movieapp.ui.people.PeoplePage

@Composable
fun NavHostPage() {
    val navController: NavHostController = rememberNavController()
    var bottomNavigationIndex by rememberSaveable { mutableStateOf(0) }
    NavHost(
        navController = navController,
        startDestination = NavigationPage.BottomNavigationPage.routeName
    ) {
        composable(NavigationPage.BottomNavigationPage.routeName) {
            BottomNavigationPage(
                navController = navController,
                bottomNavigationIndex = bottomNavigationIndex,
                onBottomNavigation = { bottomNavigationIndex = it }
            )
        }
        composable(
            route = NavigationPage.MovieDetail.routeWithArgument,
            arguments = listOf(
                navArgument(NavigationPage.MovieDetail.id) { type = NavType.LongType },
            )
        ) {
            DetailPage(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable(
            route = NavigationPage.PeopleDetail.routeWithArgument,
            arguments = listOf(
                navArgument(NavigationPage.PeopleDetail.id) { type = NavType.LongType },
            )
        ) {
            PeoplePage(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}

sealed class NavigationPage(val routeName: String) {

    data object BottomNavigationPage : NavigationPage("BottomNavigationPage")
    data object Popular : NavigationPage("Popular")
    data object Bookmarks : NavigationPage("Bookmarks")

    data object MovieDetail : NavigationPage("MovieDetail") {
        const val routeWithArgument: String = "MovieDetail/{movieId}"
        const val id: String = "movieId"
    }

    data object PeopleDetail : NavigationPage("PeopleDetail") {
        const val routeWithArgument: String = "PeopleDetail/{peopleId}"
        const val id: String = "peopleId"
    }
}