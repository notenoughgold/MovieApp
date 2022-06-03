package com.altayiskender.movieapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.altayiskender.movieapp.data.Result
import com.altayiskender.movieapp.domain.models.Movie
import com.altayiskender.movieapp.domain.usecases.GetPopularMoviesUseCase
import timber.log.Timber

class MoviePagingSource(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            when (val result = getPopularMoviesUseCase.invoke(nextPage)) {
                is Result.Success -> {
                    LoadResult.Page(
                        data = result.data.results,
                        prevKey = if (nextPage == 1) null else nextPage - 1,
                        nextKey = result.data.page.plus(1)
                    )
                }
                is Result.Error -> throw result.throwable
            }
        } catch (e: Throwable) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}