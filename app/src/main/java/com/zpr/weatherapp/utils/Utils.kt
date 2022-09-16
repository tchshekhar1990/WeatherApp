package com.zpr.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class Utils {
    companion object {
        fun getDay(date: String): String {
            val dateFormat_yyyyMMddHHmmss = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.ENGLISH
            )
            val date = dateFormat_yyyyMMddHHmmss.parse(date)
            val calendar = Calendar.getInstance()
            calendar.time = date

            val dayOfWeekString =
                calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)
            return dayOfWeekString
        }

        fun convertToTemp(temp: Double?): String {
            return temp?.roundToInt().toString() + "\u00B0"
        }
    }
}