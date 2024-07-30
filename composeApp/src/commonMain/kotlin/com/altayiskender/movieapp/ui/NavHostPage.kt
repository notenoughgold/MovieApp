package com.altayiskender.movieapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.altayiskender.movieapp.ui.details.DetailPage
import com.altayiskender.movieapp.ui.details.DetailViewModel
import com.altayiskender.movieapp.ui.people.PeoplePage
import com.altayiskender.movieapp.ui.people.PeopleViewModel
import movieapp.composeapp.generated.resources.Res
import movieapp.composeapp.generated.resources.bookmarks
import movieapp.composeapp.generated.resources.popular
import movieapp.composeapp.generated.resources.search
import org.jetbrains.compose.resources.StringResource
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavHostPage() {
    KoinContext {
        val navController: NavHostController = rememberNavController()
        var bottomNavigationIndex by rememberSaveable { mutableIntStateOf(0) }

        NavHost(
            navController = navController,
            startDestination = NavigationRoute.BottomNavigationPage.routeName
        ) {
            composable(NavigationRoute.BottomNavigationPage.routeName) {
                BottomNavigationPage(
                    navController = navController,
                    bottomNavigationIndex = bottomNavigationIndex,
                    onBottomNavigation = { bottomNavigationIndex = it }
                )
            }
            composable(
                route = NavigationRoute.ParametricRoute.MovieDetail.routeName,
                arguments = listOf(
                    navArgument(NavigationRoute.ParametricRoute.MovieDetail.argumentName) { type = NavType.LongType },
                )
            ) {
                DetailPage(
                    viewModel = koinViewModel<DetailViewModel>(),
                    navController = navController
                )
            }
            composable(
                route = NavigationRoute.ParametricRoute.PeopleDetail.routeName,
                arguments = listOf(
                    navArgument(NavigationRoute.ParametricRoute.PeopleDetail.argumentName) { type = NavType.LongType },
                )
            ) {
                PeoplePage(
                    viewModel = koinViewModel<PeopleViewModel>(),
                    navController = navController
                )
            }
        }
    }
}

sealed class NavigationRoute(open val routeName: String) {

    sealed class BottomNavigationRoute(
        override val routeName: String,
        val label: StringResource
    ) : NavigationRoute(routeName) {
        data object Popular : BottomNavigationRoute("Popular", Res.string.popular)
        data object Search : BottomNavigationRoute("Search", Res.string.search)
        data object Bookmarks : BottomNavigationRoute("Bookmarks", Res.string.bookmarks)
    }

    data object BottomNavigationPage : NavigationRoute("BottomNavigationPage")

    sealed class ParametricRoute(
        override val routeName: String,
        val routeRoot: String,
        val argumentName: String,
    ) : NavigationRoute(routeName) {

        data object MovieDetail : ParametricRoute(
            routeName = "MovieDetail/{movieId}",
            routeRoot = "MovieDetail",
            argumentName = "movieId"
        )

        data object PeopleDetail : ParametricRoute(
            routeName = "PeopleDetail/{peopleId}",
            routeRoot = "PeopleDetail",
            argumentName = "peopleId"
        )

    }

}
