package com.test.moviedb.DataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.moviedb.api.Api
import com.test.moviedb.api.RetrofitHelper
import com.test.moviedb.room.model.MovieTable

class MoviesDataSource(
) : PagingSource<Int, MovieTable>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieTable> {
        return try {
            val nextPageNumber = params.key ?: 1
            Log.d("TEST_S", "Page : $nextPageNumber")
            val api = RetrofitHelper.getInstance().create(Api::class.java)

            val response = api.getMovieList(nextPageNumber.toString())
            LoadResult.Page(
                data = response.data,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.total_pages) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieTable>): Int? {
        TODO("Not yet implemented")
    }
}