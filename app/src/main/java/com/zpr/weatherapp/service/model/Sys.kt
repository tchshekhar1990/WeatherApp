package com.zpr.weatherapp.service.model

import com.google.gson.annotations.SerializedName

data class Sys(@SerializedName("pod" ) var pod : String? = null)
