package com.zpr.weatherapp.service.repository

import androidx.lifecycle.MutableLiveData
import com.zpr.weatherapp.service.ApiClient
import com.zpr.weatherapp.service.model.CurrentWeatherResponse
import com.zpr.weatherapp.service.model.WeatherForecastResponse
import com.zpr.weatherapp.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WeatherInfoRepository {
    private var apiInterface: ApiInterface? = null

    init {
        apiInterface = ApiClient.client.create(ApiInterface::class.java)
    }

    fun getWeatherForecast(
        lat: Double,
        lon: Double,
        mutableLiveData: MutableLiveData<Resource<WeatherForecastResponse>>
    ): MutableLiveData<Resource<WeatherForecastResponse>> {
        mutableLiveData.setValue(Resource.loading(null))
        apiInterface?.getWeatherForecast(lat, lon)
            ?.enqueue(object : Callback<WeatherForecastResponse> {
                override fun onResponse(
                    call: Call<WeatherForecastResponse>,
                    response: Response<WeatherForecastResponse>
                ) {
                    if (response.body() != null) {
                        mutableLiveData.value = Resource.success(
                            response.body()!!
                        )
                    }
                }

                override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                    mutableLiveData.value = Resource.error(t.message!!, null)
                }
            })
        return mutableLiveData
    }

    fun getCurrentWeatherForecast(
        lat: Double,
        lon: Double,
        mutableLiveData: MutableLiveData<Resource<CurrentWeatherResponse>>
    ): MutableLiveData<Resource<CurrentWeatherResponse>> {
        mutableLiveData.setValue(Resource.loading(null))
        apiInterface?.getCurrentWeather(lat, lon)
            ?.enqueue(object : Callback<CurrentWeatherResponse> {
                override fun onResponse(
                    call: Call<CurrentWeatherResponse>,
                    response: Response<CurrentWeatherResponse>
                ) {
                    if (response.body() != null) {
                        mutableLiveData.value = Resource.success(
                            response.body()!!
                        )
                    }
                }

                override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                    mutableLiveData.value = Resource.error(t.message!!, null)
                }
            })
        return mutableLiveData
    }
}