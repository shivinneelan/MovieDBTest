package com.test.moviedb.api

import com.test.moviedb.room.model.MovieTable
import retrofit2.http.*
import retrofit2.http.GET


interface Api {


    @GET("passenger")
    suspend fun getMovieList(@Query("page") page: String, @Query("size") size: String): MovieRequest

}