package com.zpr.weatherapp.service.model

import com.google.gson.annotations.SerializedName

data class City(@SerializedName("id"         ) var id         : Int?    = null,
                @SerializedName("name"       ) var name       : String? = null,
                @SerializedName("coord"      ) var coord      : Coord?  ,
                @SerializedName("country"    ) var country    : String? = null,
                @SerializedName("population" ) var population : Int?    = null,
                @SerializedName("timezone"   ) var timezone   : Int?    = null,
                @SerializedName("sunrise"    ) var sunrise    : Int?    = null,
                @SerializedName("sunset"     ) var sunset     : Int?    = null)
