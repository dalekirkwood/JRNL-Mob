package com.dale.jrnlmob.data.location

import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class LocationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    suspend fun getCurrentLocation(): LocationResult {
        return suspendCancellableCoroutine { continuation ->
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null && continuation.isActive) {
                        val address = reverseGeocode(location)
                        continuation.resume(
                            LocationResult.Success(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                address = address
                            )
                        )
                    } else {
                        if (continuation.isActive) {
                            continuation.resume(LocationResult.Error("Could not get location"))
                        }
                    }
                }.addOnFailureListener { e ->
                    if (continuation.isActive) {
                        continuation.resume(LocationResult.Error(e.message ?: "Location error"))
                    }
                }
            } catch (e: SecurityException) {
                if (continuation.isActive) {
                    continuation.resume(LocationResult.Error("Location permission denied"))
                }
            }
        }
    }

    private fun reverseGeocode(location: Location): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                addresses[0].getAddressLine(0)?.takeIf { it.isNotBlank() }
                    ?: "%.4f, %.4f".format(location.latitude, location.longitude)
            } else {
                "%.4f, %.4f".format(location.latitude, location.longitude)
            }
        } catch (e: Exception) {
            "%.4f, %.4f".format(location.latitude, location.longitude)
        }
    }
}

sealed class LocationResult {
    data class Success(
        val latitude: Double,
        val longitude: Double,
        val address: String
    ) : LocationResult()
    data class Error(val message: String) : LocationResult()
}
