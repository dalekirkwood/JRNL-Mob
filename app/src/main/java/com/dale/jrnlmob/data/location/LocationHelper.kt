package com.dale.jrnlmob.data.location

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
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
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @Suppress("DEPRECATION")
    suspend fun getCurrentLocation(): LocationResult {
        return suspendCancellableCoroutine { continuation ->
            try {
                val cached = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                if (cached != null) {
                    if (continuation.isActive) {
                        continuation.resume(
                            LocationResult.Success(
                                latitude = cached.latitude,
                                longitude = cached.longitude,
                                address = reverseGeocode(cached)
                            )
                        )
                    }
                    return@suspendCancellableCoroutine
                }

                val listener = object : LocationListener {
                    override fun onLocationChanged(loc: Location) {
                        locationManager.removeUpdates(this)
                        if (continuation.isActive) {
                            continuation.resume(
                                LocationResult.Success(
                                    latitude = loc.latitude,
                                    longitude = loc.longitude,
                                    address = reverseGeocode(loc)
                                )
                            )
                        }
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onProviderDisabled(provider: String) {}

                    @Deprecated("Deprecated in Java")
                    override fun onProviderEnabled(provider: String) {}

                    @Deprecated("Deprecated in Java")
                    override fun onStatusChanged(provider: String, status: Int, extras: Bundle?) {}
                }

                continuation.invokeOnCancellation {
                    locationManager.removeUpdates(listener)
                }

                try {
                    locationManager.requestSingleUpdate(
                        LocationManager.NETWORK_PROVIDER,
                        listener,
                        Looper.getMainLooper()
                    )
                } catch (e: SecurityException) {
                    if (continuation.isActive) {
                        continuation.resume(LocationResult.Error("Location permission denied"))
                    }
                } catch (e: IllegalArgumentException) {
                    if (continuation.isActive) {
                        continuation.resume(LocationResult.Error("No location provider available"))
                    }
                }
            } catch (e: SecurityException) {
                if (continuation.isActive) {
                    continuation.resume(LocationResult.Error("Location permission denied"))
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun reverseGeocode(location: Location): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
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
