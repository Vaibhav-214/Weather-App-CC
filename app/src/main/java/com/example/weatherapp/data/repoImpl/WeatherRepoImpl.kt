package com.example.weatherapp.data.repoImpl

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.data.mappers.toWeatherInfo
import com.example.weatherapp.data.remote.WeatherApiService
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.Result
import com.example.weatherapp.domain.weather.WeatherInfo
import java.lang.Exception
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherInfo(lattitude: Double, longitude: Double): Result<WeatherInfo> {
       return try {
           Result.Success(
               data = apiService.getWeatherData(
                   latitude = lattitude,
                   longitude = longitude
               ).toWeatherInfo()
           )
       } catch (e: Exception) {
           e.printStackTrace()
           Result.Error(e.message ?: "An unknown error occurred.")

       }
    }

}