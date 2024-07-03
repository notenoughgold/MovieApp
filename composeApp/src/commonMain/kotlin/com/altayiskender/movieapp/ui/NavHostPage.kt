package com.altayiskender.movieapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.altayiskender.movieapp.domain.usecases.DeleteBookmarkUseCase
import com.altayiskender.movieapp.domain.usecases.GetBookmarkStatusUseCase
import com.altayiskender.movieapp.domain.usecases.GetMovieDetailUseCase
import com.altayiskender.movieapp.domain.usecases.GetPersonDetailUseCase
import com.altayiskender.movieapp.domain.usecases.InsertBookmarkUseCase
import com.altayiskender.movieapp.ui.details.DetailPage
import com.altayiskender.movieapp.ui.details.DetailViewModel
import com.altayiskender.movieapp.ui.people.PeoplePage
import com.altayiskender.movieapp.ui.people.PeopleViewModel
import movieapp.composeapp.generated.resources.Res
import movieapp.composeapp.generated.resources.bookmarks
import movieapp.composeapp.generated.resources.popular
import org.jetbrains.compose.resources.StringResource
import org.koin.compose.koinInject

@Composable
fun NavHostPage() {
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
            val getMovieDetailUseCase: GetMovieDetailUseCase = koinInject()
            val insertBookmarkUseCase: InsertBookmarkUseCase = koinInject()
            val deleteBookmarkUseCase: DeleteBookmarkUseCase = koinInject()
            val getBookmarkStatusUseCase: GetBookmarkStatusUseCase = koinInject()
            val viewModel = viewModel {
                DetailViewModel(
                    stateHandle = createSavedStateHandle(),
                    getMovieDetailUseCase = getMovieDetailUseCase,
                    insertBookmarkUseCase = insertBookmarkUseCase,
                    deleteBookmarkUseCase = deleteBookmarkUseCase,
                    getBookmarkStatusUseCase = getBookmarkStatusUseCase
                )
            }
            DetailPage(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(
            route = NavigationRoute.PeopleDetail.routeWithArgument,
            arguments = listOf(
                navArgument(NavigationRoute.PeopleDetail.id) { type = NavType.LongType },
            )
        ) {
            val getPersonDetailUseCase: GetPersonDetailUseCase = koinInject()
            val viewModel = viewModel {
                PeopleViewModel(
                    stateHandle = createSavedStateHandle(),
                    getPersonDetailUseCase = getPersonDetailUseCase
                )
            }
            PeoplePage(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

sealed class NavigationRoute(open val routeName: String) {

    sealed class BottomNavigationRoute(
        override val routeName: String,
        val label: StringResource
    ) : NavigationRoute(routeName) {
        data object Popular : BottomNavigationRoute("Popular", Res.string.popular)
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
