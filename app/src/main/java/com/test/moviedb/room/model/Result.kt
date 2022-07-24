package com.test.moviedb.room.model

import android.os.Parcel
import android.os.Parcelable

data class Result(
    var status: String?=null,
    var message: String?=null,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),

    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Result> {
        override fun createFromParcel(parcel: Parcel): Result {
            return Result(parcel)
        }

        override fun newArray(size: Int): Array<Result?> {
            return arrayOfNulls(size)
        }
    }
}
