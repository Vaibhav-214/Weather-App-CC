package com.example.weatherapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//
//@JsonClass(generateAdapter = true)
//data class WeatherDataDto (
//    val time: List<String>,
//    @Json(name = "temperature_2m")
//    val temperatures: List<Double>,
//    @Json(name = "relativehumidity_2m")
//    val humidities: List<Double>,
//    @Json(name = "pressure_msl")
//    val pressures : List<Double>,
//    @Json(name = "windspeed_10m")
//    val windSpeeds: List<Double>,
//    @Json(name = "weathercode")
//    val weatherCodes : List<Int>
//)
//
//@JsonClass(generateAdapter = true)
//data class WeatherDataDto (
//    val time: List<String>,
//    val temperature_2m: List<Double>,
//    val relativehumidity_2m: List<Double>,
//    val pressure_msl : List<Double>,
//    val windspeed_10m: List<Double>,
//    val weathercode : List<Int>
//)
data class WeatherDataDto(
    val time: List<String>,
    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,
    @field:Json(name = "weathercode")
    val weatherCodes: List<Int>,
    @field:Json(name = "pressure_msl")
    val pressures: List<Double>,
    @field:Json(name = "windspeed_10m")
    val windSpeeds: List<Double>,
    @field:Json(name = "relativehumidity_2m")
    val humidities: List<Double>
)
