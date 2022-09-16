package com.zpr.weatherapp.service.model

import com.google.gson.annotations.SerializedName
import com.zpr.weatherapp.utils.Utils

data class List(
    @SerializedName("dt") var dt: Double?,
    @SerializedName("main") var main: Main?,
    @SerializedName("weather") var weather: ArrayList<Weather>,
    @SerializedName("clouds") var clouds: Clouds?,
    @SerializedName("wind") var wind: Wind?,
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("pop") var pop: Double? = null,
    @SerializedName("sys") var sys: Sys?,
    @SerializedName("dt_txt") var dtTxt: String? = null
) {
    fun getDay(): String? {
        return dtTxt?.let { Utils.getDay(it) }
    }
}