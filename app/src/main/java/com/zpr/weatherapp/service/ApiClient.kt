package com.zpr.weatherapp.service

import com.zpr.weatherapp.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit? = null
    val client: Retrofit
        get() {
            if (retrofit == null) {
                synchronized(Retrofit::class.java) {
                    if (retrofit == null) {
                        retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                    }
                }

            }
            return retrofit!!
        }
}