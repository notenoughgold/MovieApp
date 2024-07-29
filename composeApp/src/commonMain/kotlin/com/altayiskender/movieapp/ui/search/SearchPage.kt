package com.altayiskender.movieapp.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.ui.common.PopularMovieItem

@Composable
internal fun SearchPage(
    viewModel: SearchViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val lazySearchResultItems: LazyPagingItems<Movie> = viewModel.searchResults.collectAsLazyPagingItems()
    val gridState = rememberLazyGridState()
    val textInput by viewModel.textInput.collectAsState("")

    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            value = textInput,
            onValueChange = viewModel::onTextInputChange,
            trailingIcon = {
                if (textInput.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { viewModel.onTextInputChange("") })
                }
            }
        )

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            state = gridState,
        ) {
            items(
                count = lazySearchResultItems.itemCount,
                itemContent = { index: Int ->
                    lazySearchResultItems[index]?.let { PopularMovieItem(movie = it, navController = navController) }
                }
            )
        }
    }
}
