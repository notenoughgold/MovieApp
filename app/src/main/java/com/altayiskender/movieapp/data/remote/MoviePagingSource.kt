package com.altayiskender.movieapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.usecases.GetPopularMoviesUseCase
import timber.log.Timber

class MoviePagingSource(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val nextPage = params.key ?: 1
        val result = getPopularMoviesUseCase.invoke(nextPage).getOrElse {
            Timber.e(it)
            return LoadResult.Error(it)
        }

        return LoadResult.Page(
            data = result.results,
            prevKey = if (nextPage == 1) null else nextPage - 1,
            nextKey = result.page.plus(1)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
