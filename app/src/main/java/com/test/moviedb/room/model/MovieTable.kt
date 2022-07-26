package com.test.moviedb.room.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.Nullable

@Parcelize
@Entity(tableName = "movieTable")
data class MovieTable(
    @PrimaryKey var _id: String,
    var name: String,
    var trips: String,
//    @Nullable
//    var avatar: String,
) : Parcelable