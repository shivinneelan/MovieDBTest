package com.test.moviedb.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.moviedb.room.model.MovieTable

@Database(entities = [MovieTable::class], version = 1, exportSchema = false)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun movieDao(): DbDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: RoomDataBase? = null

        fun getDatabase(context: Context): RoomDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDataBase::class.java,
                    "movieDB"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}