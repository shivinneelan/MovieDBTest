package com.test.moviedb.dataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.moviedb.room.DbDao
import com.test.moviedb.room.model.MovieTable

class MoviesDbDataSource(
    private val dao: DbDao
) : PagingSource<Int, MovieTable>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieTable> {
        return try {
            val nextPageNumber = params.key ?: 0
            Log.d("TEST_S", "Db Page : $nextPageNumber")
            val entities = dao.getPagedList(params.loadSize, nextPageNumber * params.loadSize)
            Log.d("TEST_S", "Db size : ${entities.size}")

            LoadResult.Page(
                data = entities,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = if (entities.isEmpty()) null else nextPageNumber + 1

            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieTable>): Int? {
        TODO("Not yet implemented")
    }
}