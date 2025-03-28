package com.jriveiro.randomuser.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

const val DEFAULT_REGION = "ES"

suspend fun Context.getRegion(): String {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    val location = fusedLocationClient.lastLocation()

    val geocoder = Geocoder(this)
    val addresses = location?.let {
        geocoder.getFromLocationCompat(it.latitude, it.longitude, 1)
    }

    val region = addresses?.firstOrNull()?.countryCode
    return region ?: DEFAULT_REGION
}

@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.lastLocation(): Location? {
    return suspendCancellableCoroutine { continuation ->
        lastLocation.addOnSuccessListener { location ->
            continuation.resume(location)
        }.addOnFailureListener {
            continuation.resume(null)
        }
    }
}

suspend fun Geocoder.getFromLocationCompat(
    latitude: Double,
    longitude: Double,
    maxResults: Int
): List<Address> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    suspendCancellableCoroutine { continuation ->
        getFromLocation(latitude, longitude, maxResults, object : Geocoder.GeocodeListener {
            override fun onGeocode(addresses: MutableList<Address>) {
                continuation.resume(addresses)
            }

            override fun onError(errorMessage: String?) {
                continuation.resume(emptyList())
            }
        })
    }
} else {
    withContext(Dispatchers.IO) {
        @Suppress("DEPRECATION")
        getFromLocation(latitude, longitude, maxResults) ?: emptyList()
    }
}