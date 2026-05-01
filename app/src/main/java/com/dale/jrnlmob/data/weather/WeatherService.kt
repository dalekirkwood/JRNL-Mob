package com.dale.jrnlmob.data.weather

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

data class WeatherResult(
    val temperature: String,
    val condition: String,
    val icon: String
)

@Singleton
class WeatherService @Inject constructor() {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        temperatureUnit: String = "celsius"
    ): WeatherResult? {
        return withContext(Dispatchers.IO) {
            try {
                val unitParam = if (temperatureUnit == "fahrenheit") "fahrenheit" else "celsius"
                val unitLabel = if (unitParam == "fahrenheit") "°F" else "°C"
                val url = "https://api.open-meteo.com/v1/forecast?" +
                    "latitude=$latitude&longitude=$longitude" +
                    "&current=temperature_2m,weather_code" +
                    "&temperature_unit=$unitParam"
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                val body = response.body?.string() ?: return@withContext null
                val json = JSONObject(body)
                val current = json.getJSONObject("current")
                val temp = current.getDouble("temperature_2m")
                val code = current.getInt("weather_code")
                val condition = weatherCodeToString(code)
                WeatherResult(
                    temperature = "%.0f$unitLabel".format(temp),
                    condition = condition,
                    icon = weatherCodeToEmoji(code)
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    private fun weatherCodeToString(code: Int): String = when (code) {
        0 -> "Clear"
        1 -> "Mostly clear"
        2 -> "Partly cloudy"
        3 -> "Overcast"
        45, 48 -> "Fog"
        51, 53, 55 -> "Drizzle"
        61, 63, 65 -> "Rain"
        71, 73, 75 -> "Snow"
        80, 81, 82 -> "Rain showers"
        95, 96, 99 -> "Thunderstorm"
        else -> "Unknown"
    }

    private fun weatherCodeToEmoji(code: Int): String = when (code) {
        0 -> "☀️"
        1 -> "🌤"
        2 -> "⛅"
        3 -> "☁️"
        45, 48 -> "🌫"
        51, 53, 55 -> "🌦"
        61, 63, 65 -> "🌧"
        71, 73, 75 -> "🌨"
        80, 81, 82 -> "🌦"
        95, 96, 99 -> "⛈"
        else -> "🌡"
    }
}
