package com.test.moviedb.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.room.Dao
import com.test.moviedb.room.model.MovieTable


@Dao
interface DbDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieList(data: List<MovieTable>)

    @Query("SELECT * FROM movieTable")
    suspend fun getMovieList(): List<MovieTable>

}