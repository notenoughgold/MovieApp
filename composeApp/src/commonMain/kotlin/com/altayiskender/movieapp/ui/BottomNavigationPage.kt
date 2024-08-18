package com.altayiskender.movieapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.altayiskender.movieapp.ui.bookmarks.BookmarksPage
import com.altayiskender.movieapp.ui.popular.PopularPage
import com.altayiskender.movieapp.ui.search.SearchPage
import movieapp.composeapp.generated.resources.Res
import movieapp.composeapp.generated.resources.bookmarks
import movieapp.composeapp.generated.resources.popular
import movieapp.composeapp.generated.resources.search
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BottomNavigationPage(
    navController: NavHostController,
    bottomNavigationIndex: Int,
    onBottomNavigation: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val bottomNavigationModels = remember {
        listOf(
            BottomNavigationModel(HomeBottomNavigationRoute.Popular, Res.string.popular, Icons.Default.Star),
            BottomNavigationModel(HomeBottomNavigationRoute.Search, Res.string.search, Icons.Default.Search),
            BottomNavigationModel(HomeBottomNavigationRoute.Bookmarks, Res.string.bookmarks, Icons.Default.Favorite),
        )
    }

    val bottomNavController = rememberNavController().apply {
        addOnDestinationChangedListener { _, destination, _ ->
            onBottomNavigation(
                bottomNavigationModels.indexOfFirst {
                    destination.route == it.route::class.qualifiedName
                }
            )
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = { PosterAppBar(stringResource(bottomNavigationModels[bottomNavigationIndex].label)) },
        bottomBar = {
            NavigationBar {
                bottomNavigationModels.forEachIndexed { index, entry ->
                    NavigationBarItem(
                        icon = { Icon(entry.icon, stringResource(entry.label)) },
                        label = { Text(stringResource(entry.label)) },
                        selected = bottomNavigationIndex == index,
                        onClick = {
                            bottomNavController.navigate(entry.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(bottomNavController.graph.findStartDestination().route.orEmpty()) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
        content = {
            NavHost(
                bottomNavController,
                startDestination = HomeBottomNavigationRoute.Popular,
                modifier = Modifier.padding(it)
            ) {
                composable<HomeBottomNavigationRoute.Popular> {
                    PopularPage(navController = navController, viewModel = koinViewModel())
                }
                composable<HomeBottomNavigationRoute.Search> {
                    SearchPage(navController = navController, viewModel = koinViewModel())
                }
                composable<HomeBottomNavigationRoute.Bookmarks> {
                    BookmarksPage(navController = navController, viewModel = koinViewModel())
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PosterAppBar(title: String) {
    TopAppBar(
        title = {
            Text(text = title)
        }
    )
}

private data class BottomNavigationModel(
    val route: HomeBottomNavigationRoute,
    val label: StringResource,
    val icon: ImageVector
)
