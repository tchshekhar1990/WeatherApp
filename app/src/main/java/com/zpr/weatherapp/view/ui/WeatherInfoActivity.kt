package com.zpr.weatherapp.view.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.zpr.weatherapp.R
import com.zpr.weatherapp.service.model.CurrentWeatherResponse
import com.zpr.weatherapp.utils.GPSUtils
import com.zpr.weatherapp.utils.GPS_REQUEST
import com.zpr.weatherapp.utils.Resource
import com.zpr.weatherapp.utils.Utils
import com.zpr.weatherapp.view.adapter.ForecastListAdapter
import com.zpr.weatherapp.viewmodel.WeatherInfoViewModel
import kotlinx.android.synthetic.main.activity_main.*

class WeatherInfoActivity : AppCompatActivity() {
    lateinit var viewModel: WeatherInfoViewModel

    private var listAdapter: ForecastListAdapter = ForecastListAdapter()

    private var isGPSEnabled = false

    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(WeatherInfoViewModel::class.java)
        locationPermission.setOnClickListener { fetchPermission() }
        upCompingDaysInfoRcv.adapter = listAdapter
        initLocationAction()
    }

    private fun initLocationAction() {
        when {
            checkPermission() -> startLocationUpdated()
            else -> fetchPermission()
        }
    }

    private fun startLocationUpdated() {
        enableGps()
        when {
            !isGPSEnabled -> Toast.makeText(
                this,
                "Please Enable your location",
                Toast.LENGTH_LONG
            ).show()
            else -> {
                viewModel.apply {
                    getLocationData.observe(this@WeatherInfoActivity) {
                        loadWeatherForecast(
                            it.latitude, it.longitude
                        ).observe(this@WeatherInfoActivity) {
                            when(it.status){
                                Resource.Status.SUCCESS ->{
                                    it.data?.list?.let { it1 -> listAdapter.setForecastList(it1) }
                                }
                                Resource.Status.LOADING ->{

                                }
                                Resource.Status.ERROR ->{

                                }
                            }
                        }

                        loadCurrentWeatherForecast(it.latitude, it.longitude).observe(
                            this@WeatherInfoActivity
                        ) {
                            when(it.status){
                                Resource.Status.SUCCESS ->{
                                    hideLoading()
                                    updateUI(it.data)
                                }
                                Resource.Status.LOADING ->{
                                    showLoading()
                                }
                                Resource.Status.ERROR ->{
                                    hideLoading()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fetchPermission() {
        return ActivityCompat.requestPermissions(
            this,
            locationPermissions, 101
        )
    }

    private fun checkPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermission.visibility = View.GONE
                    startLocationUpdated()
                } else {
                    Log.d("MainActivity", "onRequestPermissionsResult Canceled")
                   locationPermission.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GPS_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                isGPSEnabled = true
                initLocationAction()
            }
        }
    }

    private fun updateUI(data: CurrentWeatherResponse?) {
        data?.let {
            temperature.text = Utils.convertToTemp(it.main?.temp)
            forecast.text = it.weather[0].main
            minTemperature.text =
                getString(R.string.min_temp, Utils.convertToTemp(it.main?.tempMin))
            maxTemperature.text =
                getString(R.string.max_temp, Utils.convertToTemp(it.main?.tempMax))
            currentTemperature.text =
                getString(R.string.current_temp, Utils.convertToTemp(it.main?.temp))
            when (it.weather[0].id) {
                in 500..531 -> updateWeatherUIBackGround(R.drawable.forest_rainy, R.color.rainy)
                800 -> updateWeatherUIBackGround(R.drawable.forest_sunny, R.color.sunny)
                else -> updateWeatherUIBackGround(R.drawable.forest_cloudy, R.color.cloudy)
            }
        }
    }

    private fun updateWeatherUIBackGround(bgImage: Int, color: Int) {
        forecastBg.setBackgroundResource(bgImage)
        appBackground.setBackgroundColor(ContextCompat.getColor(this, color))
        window?.statusBarColor = ContextCompat.getColor(this, color)
    }

    private fun enableGps() {
        GPSUtils(this).turnGPSOn(object : GPSUtils.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                isGPSEnabled = isGPSEnable
            }
        })
    }

    fun showLoading() {
        mShimmerViewContainer.startShimmer()
        mShimmerViewContainer.visibility = View.VISIBLE
    }

    fun hideLoading() {
        mShimmerViewContainer.stopShimmer()
        mShimmerViewContainer.visibility = View.GONE
    }
}