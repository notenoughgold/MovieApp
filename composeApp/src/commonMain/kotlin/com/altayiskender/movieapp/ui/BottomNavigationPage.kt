package com.altayiskender.movieapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.altayiskender.movieapp.ui.bookmarks.BookmarksPage
import com.altayiskender.movieapp.ui.popular.PopularPage
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BottomNavigationPage(
    navController: NavHostController,
    bottomNavigationIndex: Int,
    onBottomNavigation: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val navBarEntries = listOf(
        NavigationRoute.BottomNavigationRoute.Popular,
        NavigationRoute.BottomNavigationRoute.Bookmarks
    )
    val icons = listOf(Icons.Filled.Star, Icons.Filled.Favorite)
    val bottomNavController = rememberNavController().apply {
        addOnDestinationChangedListener { _, destination, _ ->
            onBottomNavigation(
                navBarEntries.indexOfFirst {
                    destination.route == it.routeName
                }
            )
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = { PosterAppBar(stringResource(navBarEntries[bottomNavigationIndex].label)) },
        bottomBar = {
            NavigationBar {
                navBarEntries.forEachIndexed { index, entry ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], null) },
                        label = { Text(stringResource(entry.label)) },
                        selected = bottomNavigationIndex == index,
                        onClick = {
                            bottomNavController.navigate(entry.routeName) {
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
                startDestination = NavigationRoute.BottomNavigationRoute.Popular.routeName,
                modifier = Modifier.padding(it)
            ) {
                composable(
                    NavigationRoute.BottomNavigationRoute.Popular.routeName
                ) {
                    PopularPage(navController = navController, viewModel = koinViewModel())
                }
                composable(
                    NavigationRoute.BottomNavigationRoute.Bookmarks.routeName
                ) {
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
