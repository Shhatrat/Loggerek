package com.shhatrat.loggerek.manager.watch

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

object LocationService {
    @SuppressLint("MissingPermission")
    fun getLocationFlow(context: Context) = callbackFlow {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                for (location in result.locations) {
                    trySend(location)
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
        awaitClose { fusedLocationProviderClient.removeLocationUpdates(locationCallback) }
    }

    @SuppressLint("MissingPermission")
    fun update(context: Context){
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.locations.firstOrNull()?.let { location ->
//                    onLocationReceived(location)
                    println("${location.time} ${location.latitude} ${location.longitude}")
                    fusedLocationProviderClient.removeLocationUpdates(this) // Usuwamy callback po uzyskaniu lokalizacji
                }
            }
        }
//        val location = fusedLocationProviderClient.lastLocation.result
//        println("${location.time} ${location.latitude} ${location.longitude}")

//        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(context: Context, onLocationReceived: (android.location.Location?) -> Unit) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                onLocationReceived(location) // Zwróć lokalizację
            }
            .addOnFailureListener { exception ->
                onLocationReceived(null) // W przypadku błędu, zwróć null
            }
    }}