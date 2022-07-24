package com.test.moviedb.api

import com.test.moviedb.SharedPref
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitHelper {

    private const val BASE_URL = "https://reqres.in/"
    private const val API= BASE_URL +"api/"

    var TOKEN = SharedPref.read(SharedPref.DEVICE_TOKEN,"")


    fun getInstance(): Retrofit {
        TOKEN = SharedPref.read(SharedPref.DEVICE_TOKEN,"")
        return Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
//                    .addHeader("Authorization", "Bearer $TOKEN")
                    .build()
                chain.proceed(request)
            }.build())
            .build()
    }

}
