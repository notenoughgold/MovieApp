package com.altayiskender.movieapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.altayiskender.movieapp.ui.bookmarks.BookmarksPage
import com.altayiskender.movieapp.ui.popular.PopularPage

@Composable
fun BottomNavigationPage(
    navController: NavHostController,
    bottomNavigationIndex: Int,
    onBottomNavigation: (Int) -> Unit
) {
    val navBarEntries = listOf(NavigationPage.Popular, NavigationPage.Bookmarks)
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
        topBar = { PosterAppBar(navBarEntries[bottomNavigationIndex].routeName) },
        bottomBar = {
            NavigationBar {
                navBarEntries.forEachIndexed { index, entry ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], null) },
                        label = { Text(entry.routeName) },
                        selected = bottomNavigationIndex == index,
                        onClick = {
                            bottomNavController.navigate(entry.routeName) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
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
                startDestination = NavigationPage.Popular.routeName,
                modifier = Modifier.padding(it)
            ) {
                composable(
                    NavigationPage.Popular.routeName
                ) {
                    PopularPage(navController = navController)
                }
                composable(
                    NavigationPage.Bookmarks.routeName
                ) {
                    BookmarksPage(navController = navController)
                }
            }

        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PosterAppBar(title: String) {
    TopAppBar(title = {
        Text(text = title)
    })
}
