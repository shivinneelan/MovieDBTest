package com.test.moviedb.room.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movieTable")
data class MovieTable(
    @PrimaryKey var id: Int = 0,
    var first_name: String,
    var email: String,
    var avatar: String,
) : Parcelable