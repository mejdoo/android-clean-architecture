package com.mejdoo.clean.data.source.remote.implementation

import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val retrofit: Retrofit =
        createNetworkClient(BASE_URL)
val cleanApi = retrofit.create(CleanApi::class.java)

fun createNetworkClient(baseUrl: String): Retrofit {
    return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}




