package com.altayiskender.movieapp.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.altayiskender.movieapp.ui.details.DetailPage
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
                //  navArgument(Screens.MovieDetail.argument1) { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val movieId =
                backStackEntry.arguments?.getLong(NavigationPage.MovieDetail.argument0)
                    ?: return@composable
//            val movieTitle =
//                backStackEntry.arguments?.getString(Screens.MovieDetail.argument1)
//                    ?: return@composable
            DetailPage(
                movieId = movieId,
                // movieTitle = movieTitle,
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
        // const val argument1: String = "movieTitle"
    }
}