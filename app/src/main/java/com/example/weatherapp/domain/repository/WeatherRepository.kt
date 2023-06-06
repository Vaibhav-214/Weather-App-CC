package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.util.Result
import com.example.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherInfo(lattitude: Double, longitude: Double) : Result<WeatherInfo>
}