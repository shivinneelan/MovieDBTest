package com.test.moviedb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.test.moviedb.DataSource.MoviesDataSource

class MovieViewModel(
) : ViewModel() {
    val passengers = Pager(PagingConfig(pageSize = 10)) {
        MoviesDataSource()
    }.flow.cachedIn(viewModelScope)
}