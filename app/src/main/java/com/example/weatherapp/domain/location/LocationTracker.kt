package com.example.weatherapp.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation() : Location?
}

//if we want to strictly follow the clean architecture we should make a custom Location class that contains
// longitude and latitude because here we are using Location from Android system and in domain layer there
// shouldn't be any implementation details