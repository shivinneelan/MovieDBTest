package com.test.moviedb.dataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.moviedb.api.Api
import com.test.moviedb.api.RetrofitHelper
import com.test.moviedb.room.DbDao
import com.test.moviedb.room.model.MovieTable

class MoviesApiDataSource(
    private val dao: DbDao
) : PagingSource<Int, MovieTable>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieTable> {
        return try {
            val nextPageNumber = params.key ?: 1
            Log.d("TEST_S", "api Page : $nextPageNumber")
            val api = RetrofitHelper.getInstance().create(Api::class.java)
            val response = api.getMovieList(nextPageNumber.toString(),"10")
            Log.d("TEST_S", "api size : ${response.data.size}")

            try {
                dao.insertMovieList(response.data)

            }catch (e: java.lang.Exception){
                e.printStackTrace()
                e.message?.let { Log.d("TEST_S", it) }
            }
            LoadResult.Page(
                data = response.data,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.totalPages) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieTable>): Int? {
        TODO("Not yet implemented")
    }
}