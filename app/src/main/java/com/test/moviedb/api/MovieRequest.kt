package com.test.moviedb.api

import com.test.moviedb.room.model.MovieTable

data class MovieRequest(
    val page: Int,

    val per_page: Int,

    val total: Int,
    val total_pages: Int,

    val data: List<MovieTable>,
)
