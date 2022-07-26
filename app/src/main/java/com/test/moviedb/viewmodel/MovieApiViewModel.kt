package com.test.moviedb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.test.moviedb.dataSource.MoviesApiDataSource
import com.test.moviedb.room.DbDao

class MovieApiViewModel(
    private val dao: DbDao
) : ViewModel() {
    val passengers = Pager(PagingConfig(pageSize = 8)) {
        MoviesApiDataSource(dao)
    }.flow.cachedIn(viewModelScope)
}


class MovieApiViewModelFactory(
    private val dao: DbDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieApiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieApiViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
