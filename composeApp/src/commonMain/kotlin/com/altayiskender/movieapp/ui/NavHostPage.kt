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
                route = NavigationRoute.MovieDetail.routeWithArgument,
                arguments = listOf(
                    navArgument(NavigationRoute.MovieDetail.id) { type = NavType.LongType },
                )
            ) {
                DetailPage(
                    viewModel = koinViewModel<DetailViewModel>(),
                    navController = navController
                )
            }
            composable(
                route = NavigationRoute.PeopleDetail.routeWithArgument,
                arguments = listOf(
                    navArgument(NavigationRoute.PeopleDetail.id) { type = NavType.LongType },
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

    data object MovieDetail : NavigationRoute("MovieDetail") {
        const val routeWithArgument: String = "MovieDetail/{movieId}"
        const val id: String = "movieId"
    }

    data object PeopleDetail : NavigationRoute("PeopleDetail") {
        const val routeWithArgument: String = "PeopleDetail/{peopleId}"
        const val id: String = "peopleId"
    }
}
