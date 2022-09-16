package com.zpr.weatherapp.service.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("cod") var cod: String?,
    @SerializedName("main") var main: Main?,
    @SerializedName("weather") var weather: ArrayList<Weather>,
    @SerializedName("coord") var coord: Coord?,
    @SerializedName("clouds") var clouds: Clouds?,
    @SerializedName("wind") var wind: Wind?
)
