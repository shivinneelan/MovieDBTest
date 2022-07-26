package com.test.moviedb.api

import com.test.moviedb.room.model.MovieTable

data class MovieRequest(

    val totalPassengers: Int,
    val totalPages: Int,
    val data: List<MovieTable>,
)
