package com.test.moviedb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.test.moviedb.dataSource.MoviesDbDataSource
import com.test.moviedb.room.DbDao

class MovieDbViewModel(
    private val dao: DbDao
) : ViewModel() {
    val data = Pager(
        PagingConfig(
            pageSize = 8,
            enablePlaceholders = false,
            initialLoadSize = 8
        ),
    ) {
        MoviesDbDataSource(dao)
    }.flow.cachedIn(viewModelScope)
}

class MovieDbViewModelFactory(
    private val dao: DbDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieDbViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieDbViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
