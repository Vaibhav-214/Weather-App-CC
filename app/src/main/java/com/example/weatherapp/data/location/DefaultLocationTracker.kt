package com.example.weatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.weatherapp.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker @Inject constructor(
    private val locationClient : FusedLocationProviderClient, // from location library
    private val applicationContext : Application
) : LocationTracker {
    override suspend fun getCurrentLocation(): Location? {
        val hasAccessApproxLocationPermission = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION// Manifest is from android here not local
        ) == PackageManager.PERMISSION_GRANTED

        Log.d("LOC", hasAccessApproxLocationPermission.toString())

        val locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        Log.d("LOC", isGpsEnabled.toString())


        if (!hasAccessApproxLocationPermission || !isGpsEnabled) {
            return null
        }


        // we need to convert the callback to coroutine

        return suspendCancellableCoroutine {cont ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result)
                    } else {
                        cont.resume(null)
                    }
                     return@suspendCancellableCoroutine
                }
            }.addOnSuccessListener {
                cont.resume(it)
            }.addOnFailureListener {
                cont.resume(null)
            }.addOnCanceledListener {
                cont.cancel()
            }

        }
    }
}