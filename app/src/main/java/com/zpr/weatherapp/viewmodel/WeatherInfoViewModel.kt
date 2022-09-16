package com.zpr.weatherapp.viewmodel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zpr.weatherapp.service.model.CurrentWeatherResponse
import com.zpr.weatherapp.service.model.WeatherForecastResponse
import com.zpr.weatherapp.service.repository.LiveLocationData
import com.zpr.weatherapp.service.repository.WeatherInfoRepository
import com.zpr.weatherapp.utils.Resource

class WeatherInfoViewModel(application: Application) : AndroidViewModel(application) {
    private var weatherInfoLiveData = MutableLiveData<Resource<WeatherForecastResponse>>()
    private var currentWeatherInfoLiveData = MutableLiveData<Resource<CurrentWeatherResponse>>()
    private val locationData = LiveLocationData(application)

    val getLocationData: LiveData<Location> = locationData

    fun loadWeatherForecast(lat: Double, lon: Double): LiveData<Resource<WeatherForecastResponse>> {
        weatherInfoLiveData =
            WeatherInfoRepository.getWeatherForecast(lat, lon, weatherInfoLiveData)
        return weatherInfoLiveData
    }

    fun loadCurrentWeatherForecast(
        lat: Double,
        lon: Double
    ): LiveData<Resource<CurrentWeatherResponse>> {
        currentWeatherInfoLiveData =
            WeatherInfoRepository.getCurrentWeatherForecast(lat, lon, currentWeatherInfoLiveData)
        return currentWeatherInfoLiveData
    }
}