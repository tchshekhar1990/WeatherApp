package com.zpr.weatherapp.service.repository

import com.zpr.weatherapp.service.model.CurrentWeatherResponse
import com.zpr.weatherapp.service.model.WeatherForecastResponse
import com.zpr.weatherapp.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("forecast")
    fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY
    ): Call<WeatherForecastResponse>

    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY
    ): Call<CurrentWeatherResponse>

}