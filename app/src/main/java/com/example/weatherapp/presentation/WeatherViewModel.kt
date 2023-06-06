package com.example.weatherapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository : WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    private val _state = MutableStateFlow<WeatherState>(WeatherState())
    val state = _state.asStateFlow()

    fun loadWeatherInfo() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            locationTracker.getCurrentLocation()?.let { location ->

                Log.d("VM", location.toString())

                when (val result = repository.getWeatherInfo(location.latitude, location.longitude)) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location make sure to grant permission and enable GPS."
                )
            }
        }
    }


}