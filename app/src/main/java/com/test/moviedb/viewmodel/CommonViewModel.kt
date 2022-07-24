package com.test.moviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.moviedb.room.RoomDataBase
import com.test.moviedb.room.model.MovieTable
import com.test.moviedb.room.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CommonViewModel(application: Application) : AndroidViewModel(application) {
    var movieListLiveData = MutableLiveData<List<MovieTable>>()
    var movieInsertLiveData = MutableLiveData<Result>()
    val STATUS_SUCCESS = "status_success"
    val STATUS_FAILED = "status_failed"


    private val movieDao by lazy { RoomDataBase.getDatabase(application).movieDao() }

    fun getMovieLists() {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.getMovieList().also { list ->

                movieListLiveData.postValue(list)
            }
        }
    }

    fun insertMovieList(list: List<MovieTable>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieDao.insertMovieList(list)
                movieInsertLiveData.postValue(
                    Result(
                        status = STATUS_SUCCESS,
                        message = "list inserted"
                    )
                )
            }catch (e: Exception){
                movieInsertLiveData.postValue(
                    Result(
                        status = STATUS_FAILED,
                        message = "Something went wrong"
                    )
                )
            }

        }
    }


}